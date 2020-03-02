package com.master.utils;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 校验参数工具类
 */
public class ValidateUtil {

    private static boolean isFailFast = true;

    private static Validator validator = Validation
            .byProvider(HibernateValidator.class).configure().failFast(isFailFast).buildValidatorFactory().getValidator();


    public static <T> List<String> validate(T obj) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
        Iterator<ConstraintViolation<T>> it = constraintViolations.iterator();
        List<String> errorMsgList = new ArrayList<String>();
        while (it.hasNext()) {
            errorMsgList.add(it.next().getMessage());
        }
        return errorMsgList;
    }

    public static <T> void validateParamsWithExceptionThrow(T obj) throws Exception {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
        Iterator<ConstraintViolation<T>> it = constraintViolations.iterator();
        List<String> errorMsgList = new ArrayList<String>();
        while (it.hasNext()) {
            errorMsgList.add(it.next().getMessage());
        }
        if (CollectionUtils.isNotEmpty(errorMsgList)) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String errMsg : errorMsgList) {
                stringBuilder.append(errMsg).append(",");
            }
            throw new Exception(stringBuilder.toString());
        }
    }

}