package com.master.producer;

import com.master.common.constant.Constants;
import com.master.common.producer.RabbitSender;
import com.master.common.utils.FastJsonConvertUtil;
import com.master.entity.BrokerMessageLog;
import com.master.entity.TaskCore;
import com.master.mapper.BrokerMessageLogMapper;
import com.master.mapper.TaskCoreMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class RabbitTaskSender {

    //注入RabbitSender
    @Autowired
    private RabbitSender rabbitSender;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    @Autowired
    private TaskCoreMapper taskCoreMapper;


    //回调函数: confirm确认
    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.err.println("correlationData: " + correlationData);
            String messageId = correlationData.getId();
            if (ack) {
                //如果confirm返回成功 则进行更新
                brokerMessageLogMapper.updateById(new BrokerMessageLog(messageId, Constants.SEND_SUCCESS));
            } else {
                //失败则进行具体的后续操作:重试 或者补偿等手段
                log.error("异常处理...");
            }
        }
    };

    //发送消息方法调用: 构建自定义对象消息
    public String sendTask(TaskCore taskCore) {
        //task Current Time
        LocalDateTime taskTime = LocalDateTime.now();
        // log insert
        BrokerMessageLog brokerMessageLog = new BrokerMessageLog();
        brokerMessageLog.setId(taskCore.getMessageId());
        //save task message as json
        brokerMessageLog.setMessage(FastJsonConvertUtil.convertObjectToJSON(taskCore.getContents()));
        brokerMessageLog.setStatus(Constants.SENDING);
        brokerMessageLog.setNextRetry(taskTime.plus(Constants.TIMEOUT, ChronoUnit.MINUTES));
        brokerMessageLogMapper.insert(brokerMessageLog);

        Map<String, Object> contents = FastJsonConvertUtil.convertJSONToObject(taskCore.getContents(), Map.class);
        String messageId = (String) contents.get("messageId");
        Map<String, Object> properties = new HashMap<>();

        try {
            rabbitSender.send(contents, properties);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return messageId;
    }

}
