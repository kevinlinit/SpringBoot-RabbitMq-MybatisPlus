package com.master.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.master.entity.TaskCore;
import com.master.mapper.TaskCoreMapper;
import com.master.producer.RabbitTaskSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Description: 扫描定时任务
 * Created by masterl on 2020/2/27
 */
@Slf4j
@Component
public class ScanScheduleTasker {

    @Autowired
    private RabbitTaskSender rabbitTaskSender;

    @Autowired
    private TaskCoreMapper taskCoreMapper;

    @Scheduled(initialDelay = 3000, fixedDelay = 10000)
    public void reSend() {
        log.info("---------------扫描定时发送任务开始---------------");

        /**
         * pull schedule time up message
         * 查询已经到定时时间的任务集合
         */
        List<TaskCore> list = getTimeUpScheduleTask();

        list.forEach(taskCore -> {
            log.info("任务id{},定时时间为{}已经到时，正在发送...", taskCore.getId(), taskCore.getScheduleTime());
            //设置定时时间为null，视做普通任务
            taskCore.setScheduleTime(null);
            taskCoreMapper.updateById(taskCore);
            try {
                rabbitTaskSender.sendTask(taskCore);
            } catch (Exception e) {
                log.error("-----------异常处理-----------");
            }
        });

    }

    private List<TaskCore> getTimeUpScheduleTask() {
        QueryWrapper<TaskCore> wrapper = new QueryWrapper<TaskCore>();
        wrapper.le("schedule_time", LocalDateTime.now());
        return taskCoreMapper.selectList(wrapper);
    }
}
