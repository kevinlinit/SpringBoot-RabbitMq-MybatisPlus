package com.master.service;

import com.master.entity.TaskCore;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author linqiyuan
 * @since 2020-02-27
 */
public interface ITaskCoreService extends IService<TaskCore> {

    public void createTask(TaskCore taskCore);
}
