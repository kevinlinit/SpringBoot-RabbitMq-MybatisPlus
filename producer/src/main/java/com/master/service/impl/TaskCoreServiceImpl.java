package com.master.service.impl;

import com.master.constant.Constants;
import com.master.entity.BrokerMessageLog;
import com.master.entity.TaskCore;
import com.master.mapper.BrokerMessageLogMapper;
import com.master.mapper.TaskCoreMapper;
import com.master.producer.RabbitTaskSender;
import com.master.service.ITaskCoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.utils.FastJsonConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linqiyuan
 * @since 2020-02-27
 */
@Service
public class TaskCoreServiceImpl extends ServiceImpl<TaskCoreMapper, TaskCore> implements ITaskCoreService {

    //自动注入RabbitTemplate模板类
    @Autowired
    private RabbitTaskSender rabbitTaskSender;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    @Autowired
    private TaskCoreMapper taskCoreMapper;

    public String createTask(TaskCore taskCore) {
        // task current time
        LocalDateTime taskTime = LocalDateTime.now();

        // task insert
        taskCoreMapper.insert(taskCore);
        // task message sender

        // log insert
        BrokerMessageLog brokerMessageLog = new BrokerMessageLog();
        brokerMessageLog.setId(taskCore.getMessageId());
        //save task message as json
        brokerMessageLog.setMessage(FastJsonConvertUtil.convertObjectToJSON(taskCore.getContents()));
        brokerMessageLog.setStatus("0");
        brokerMessageLog.setNextRetry(taskTime.plus(Constants.TIMEOUT, ChronoUnit.MINUTES));
        brokerMessageLog.setCreateTime(LocalDateTime.now());
        brokerMessageLog.setUpdateTime(LocalDateTime.now());
        brokerMessageLogMapper.insert(brokerMessageLog);

        return rabbitTaskSender.sendTask(taskCore);

    }
}
