package com.zr.test.demo.aspects;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.util.JsonUtils;
import com.zr.test.demo.util.StringUtil;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常统一拦截
 *
 * @author lx
 * @author llyang
 * @date 2019/6/19 18:00
 */
@RestControllerAdvice
public class GlobalExceptionIntercept {
    /**
     * log4j日志
     */
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionIntercept.class);

    /**
     * 自定义异常拦截
     *
     * @param ce 自定义异常处理
     * @return 通用返回结果
     */
    @ExceptionHandler(value = CustomException.class)
    public Object errorHandler(HttpServletRequest request, CustomException ce) {
        return this.getResult(request, ce, "自定义异常", null,
                ce.getCode(), ce.getMessage(), true);
    }

    /**
     * 参数校验异常捕获
     *
     * @param ce 参数校验异常捕获
     * @return 通用返回结果
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Object errorHandler(HttpServletRequest request, MethodArgumentNotValidException ce) {
        //按需重新封装需要返回的错误信息
        String errorMsg = "";
        for (FieldError error : ce.getBindingResult().getFieldErrors()) {
            errorMsg = error.getDefaultMessage();
            //有多条也只取第一条，一步一步的验证，保障国际化的时候能正常翻译
            break;
        }
        return this.getResult(request, ce, "参数处理异常", ErrorCode.SYS_PARAM_ERR,
                null, errorMsg, false);

    }

    /**
     * Cannot deserialize value of type `java.lang.Integer` from String "adsf": not a valid Integer value
     * at [Source: (PushbackInputStream); line: 1, column: 143] (through reference chain: com.streamax.base.model.dto.PcardLogPageDTO["pagesize"])
     * <p>
     * 请求参数json格式化
     *
     * @param e 参数校验异常捕获
     * @return 通用返回结果
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public Object errorHandler(HttpServletRequest request, HttpMessageNotReadableException e) {
        Throwable throwable = e.getCause();
        String message = e.getMessage();
        if (throwable instanceof InvalidFormatException) {
            //如果是json的解析错误，返回解析错误的原因
            message = throwable.getMessage();
            //获取描述
            int indexDetail = message.indexOf("at");
            String messageDetail = message.substring(0, indexDetail);
            //获取对应的出错字段
            int indexField1 = message.lastIndexOf("[");
            int indexField2 = message.lastIndexOf("]");

            String messageField = message.substring(indexField1, indexField2 + 1);

            message = messageDetail + messageField;
            message = message.replace("\n", "");
        }
        return this.getResult(request, e, "参数错误", ErrorCode.SYS_PARAM_ERR,
                null, message, true);

    }

    /**
     * 系统异常拦截
     *
     * @param e 系统异常拦截
     * @return 返回通用返回结果
     */
    @ExceptionHandler(value = Exception.class)
    public Object errorHandler(HttpServletRequest request, Exception e) {
        return this.getResult(request, e, "系统异常", ErrorCode.SYS_UNKNOWN_ERR,
                null, e.getMessage(), true);
    }

    /**
     * 运行时异常拦截
     *
     * @param e 运行时异常拦截
     * @return 返回通用返回结果
     */
    @ExceptionHandler(value = RuntimeException.class)
    public Object errorHandler(HttpServletRequest request, RuntimeException e) {
        return this.getResult(request, e, "运行时异常", ErrorCode.SYS_UNKNOWN_ERR,
                null, e.getMessage(), true);
    }

    /**
     * 获取result
     *
     * @param request     request
     * @param e           exception
     * @param errorLogTip 输入日志的提示
     * @param errorCode   错误码枚举
     * @param errIntCode  错误码，当该值不为空，则优先使用
     * @param errorMsg    错误消息提示
     * @param logOutError 是否输出error到日志
     * @return Result / ResultS17
     */
    private Object getResult(HttpServletRequest request,
                             Exception e,
                             String errorLogTip,
                             ErrorCode errorCode,
                             Integer errIntCode,
                             String errorMsg,
                             boolean logOutError) {
        setSid(request);
        Result<?> result;
        //判断有误错误特定的错误信息
        if (StringUtil.isEmpty(errorMsg)) {
            if (e instanceof NullPointerException) {
                result = Result.fail(errorCode, "系统异常，空指针异常");
            } else {
                result = Result.fail(errorCode);
            }
        } else {
            //判断是否是int类型的错误码, 优先使用int类型的错误码
            if (errIntCode != null) {
                result = Result.fail(errIntCode, errorMsg);
            } else {
                result = Result.fail(errorCode, errorCode.getMessage());
            }
        }
        if (StringUtils.isEmpty(result.getSid())) {
            result.setSid(this.getTrackerId());
        }
        internationalHandler(result);
        //是否输出error日志
        if (logOutError) {
            logger.error(errorLogTip + "：" + e.getMessage(), e);
        }
        logger.info(errorLogTip + "拦截返回：resultJson==>sid[ERROR] => {}", JsonUtils.toJson(result));

        return result;
    }

    /**
     * 异常信息处理
     *
     * @param result 待转变的语言
     */
    private void internationalHandler(Result<?> result) {
        //国际化语言处理
        String msg = result.getMessage();
        if (StringUtils.isEmpty(msg)) {
            msg = ErrorCode.getMsgByCode(result.getCode());
        }
        result.setMessage(msg);
    }

    /**
     * 获取当前日志线程的sid
     * 后期如果有多项目之前的调用，可以考虑吧sid嵌入到请求头里面去
     * 这样在同一个调用链里面不同的项目之间就能拿到同一个sid
     *
     * @return sid
     */
    private String getTrackerId() {
        return ThreadContext.get("sid");
    }


    private String setSid(HttpServletRequest request) {
        String sId = ThreadContext.get("sid");
        if (sId == null) {
            Object sIdO = request.getAttribute("_sid");
            if (sIdO != null) {
                sId = String.valueOf(sIdO);
            }
            ThreadContext.put("sid", sId);
        }
        return sId;
    }

}
