package com.master.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.master.constant.Constants;
import com.master.entity.BrokerMessageLog;
import com.master.mapper.BrokerMessageLogMapper;
import com.master.producer.RabbitSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 重试发送Tasker
 * Created by masterl on 2020/2/28 1:29 AM
 */
@Slf4j
@Component
public class RetryMessageTasker {

    @Autowired
    private RabbitSender rabbitSender;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    @Scheduled(initialDelay = 3000, fixedDelay = 10000)
    public void reSend() {
        log.info("---------------定时任务开始---------------");

        /**
         * pull status = 0 and timeout message
         * 查询消息状态为0(发送中) 且已经超时的消息集合
         */
        List<BrokerMessageLog> list = getTimeoutAndSendingMessage();

        list.forEach(messageLog -> {
            if (messageLog.getTryCount() >= 3) {
                //update fail message
                brokerMessageLogMapper.updateById(new BrokerMessageLog(messageLog.getId(), Constants.SEND_FAILURE));
            } else {
                // resend
                brokerMessageLogMapper.updateTryCount(messageLog.getId(), new Date());
                Map<String, Object> properties = new HashMap<>();
                try {
                    rabbitSender.send(messageLog.getMessage(), properties);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("-----------异常处理-----------");
                }
            }
        });

    }

    private List<BrokerMessageLog> getTimeoutAndSendingMessage(){
        QueryWrapper<BrokerMessageLog> wrapper = new QueryWrapper<BrokerMessageLog>();
        wrapper.eq("status", Constants.SENDING).le("next_retry", LocalDateTime.now());
        return brokerMessageLogMapper.selectList(wrapper);
    }
}
