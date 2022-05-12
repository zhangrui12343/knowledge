package com.zr.test.demo.util;


import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 参数校验
 *
 * @author yangliangliang
 * @date 2018/8/2
 */
public class ValidatorUtil {
    private static final Logger logger = LoggerFactory.getLogger(ValidatorUtil.class);

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 校验参数是否服务条件
     * @param params 校验参数
     */
    public static void check(Object params) {
        if(validator == null) {
            logger.error("参数校验工具不存在，请确保jar包已经成功引入");
            return ;
        }
        // 为null直接返回
        if(params == null) {
            return ;
        }
        // 系统内置的普通类型直接返回x
        if(params.getClass().getClassLoader() == null) {
            return ;
        }
        // 校验注解信息
        Set<ConstraintViolation<Object>> result = validator.validate(params);
        if(result.size() > 0) {
            String errorMsg = "";
            for (ConstraintViolation<Object> objectConstraintViolation : result) {
                 errorMsg += StringUtils.isEmpty(errorMsg) ? objectConstraintViolation.getMessage() : ";" + objectConstraintViolation.getMessage();
                 break;
                 //errorMsg += StringUtils.isEmpty(errorMsg) ? objectConstraintViolation.getMessage() : ";" + objectConstraintViolation.getMessage();
            }
            throw new CustomException(ErrorCode.SYS_PARAM_ERR,errorMsg);
        }
    }
}
