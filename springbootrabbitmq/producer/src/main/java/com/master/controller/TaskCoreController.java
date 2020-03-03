package com.master.controller;


import com.master.common.controller.BaseController;
import com.master.entity.TaskCore;
import com.master.service.ITaskCoreService;
import com.master.common.utils.DdUtil;
import com.master.common.utils.FastJsonConvertUtil;
import com.master.common.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author linqiyuan
 * @since 2020-02-27
 */
@RestController
@RequestMapping(value = "/mqtask", method = RequestMethod.POST)
public class TaskCoreController extends BaseController {

    @Autowired
    ITaskCoreService taskCoreService;

    @PostMapping("/sendtask")
    @ResponseBody
    public String sendTask(@RequestBody Map<String, Object> params, HttpServletRequest httpRequest) {


        String messageId = (String) params.get("messageId");
        if (null == messageId) {
            messageId = DdUtil.getDdId();
        }
        //把messageId放到透传的content里
        Map<String, Object> content = (Map<String, Object>) params.get("content");
        content.put("messageId", messageId);

        TaskCore taskCore = new TaskCore();
        taskCore = JsonUtil.jsonMapToBean(params, TaskCore.class);
        taskCore.setMessageId(messageId);
        taskCore.setContents(FastJsonConvertUtil.convertObjectToJSON(content));
        taskCoreService.createTask(taskCore);
        return messageId;
    }
}
