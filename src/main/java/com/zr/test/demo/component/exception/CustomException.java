package com.zr.test.demo.component.exception;

import com.zr.test.demo.config.enums.ErrorCode;
import lombok.Data;

/**
 * 自定义异常
 *
 * @author yangliangliang
 * @date 2018/11/1
 */
@Data
public class CustomException extends RuntimeException{

    private static final long serialVersionUID = 9025394360504159308L;

    /**
     * 错误码
     */
    private int code;

    public CustomException(int code ,String message) {
        super(message);
        this.code = code;
    }

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public CustomException(ErrorCode errorCode,String message) {
        super(message);
        this.code = errorCode.getCode();
    }
}
