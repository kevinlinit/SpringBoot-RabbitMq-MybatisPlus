package com.master.controller;

import com.master.utils.ValidateUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public abstract class BaseController {
    protected void validateRequestData(Object requestData) throws Exception {
        List<String> errorMsgList = ValidateUtil.validate(requestData);
        if (errorMsgList != null && errorMsgList.size() > 0) {
            String errorMsg = StringUtils.join(errorMsgList, ", ");
            throw new Exception();
        }
    }

}
