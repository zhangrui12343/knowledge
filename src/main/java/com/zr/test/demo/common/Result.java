package com.zr.test.demo.common;


import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.util.StringUtil;


/**
 * 返回结果基本类
 *
 * @author llyang
 * @date 2018/10/12 10:50
 */
public class Result<T> extends BaseResult {
    /**
     * 返回数据
     */
    private T data;


    /**
     * 创建result
     * @param <T> 泛型参数
     * @return 泛型返回值
     */
    public static <T> Result<T> create() {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        return result;
    }

    /**
     * 成功
     * @return 布尔值
     */
    public static Result<Boolean> success() {
        return Result.success(true);
    }

    /**
     * 成功返回 有数据量的放回
     * @param data 泛型参数
     * @param <T> 泛型参数
     * @return 泛型返回值
     */
    @SuppressWarnings("unchecked")
    public static<T> Result<T> success(T data) {
        Result<T> result = Result.create();
        result.setSuccess(true);
        result.setCode(ErrorCode.SUCCESS.getCode());
        // 处理data中的分页信息 自定义PageInfo
        if (data instanceof PageInfo) {
            PageInfo<T> temp = (PageInfo<T>) data;
            result.setData((T) temp.getList());
            result.setPageSize(temp.getPageSize());
            result.setPage(temp.getPage());
            result.setTotal(temp.getTotal());
            result.setData((T) temp.getList());
        }else {
            result.setData(data);
        }
        return result;
    }


    /**
     * 返回失败
     * @param errorCode 有错误码的返回
     * @param data 针对Void
     * @param <T> 针对Void
     * @return Void
     */
    public static<T> Result<T> failNull(ErrorCode errorCode, T data) {
        return fail(errorCode, data);
    }

    /**
     * 成功失败
     * @param errorCode 有错误码的返回
     * @param data 泛型参数
     * @param <T> 泛型参数
     * @return 泛型返回值
     */
    public static<T> Result<T> fail(ErrorCode errorCode, T data) {
        Result<T> result = Result.create();
        result.setSuccess(true);
        result.setCode(errorCode.getCode());
        result.setMessage(errorCode.getMessage());
        result.setData(data);
        return result;
    }
    /**
     * 成功失败
     * @param errorCode 有错误码的返回
     * @param data 泛型参数
     * @param <T> 泛型参数
     * @return 泛型返回值
     */
    public static<T> Result<T> fail(ErrorCode errorCode,String message, T data) {
        Result<T> result = Result.create();
        result.setSuccess(true);
        result.setCode(errorCode.getCode());
        result.setMessage(StringUtil.isEmpty(message)?errorCode.getMessage():message);
        result.setData(data);
        return result;
    }

    /**
     * 获取数据
     * @return 泛型参数
     */
    public T parseData() {
        //如果异常则抛出异常
        if(!this.success) {
            throw new CustomException(this.code,this.message);
        }
        return this.getData();
    }

    /**
     * 验证结果
     * @return 泛型参数
     */
    public Result<T> validData() {
        if(!this.success) {
            throw new CustomException(this.code,this.message);
        }
        return this;
    }

    /**
     * 失败返回
     * @param code 错误码
     * @param message 错误提示
     * @return 返回为空
     */
    public static Result<Object> fail(ErrorCode code, String message) {
        Result<Object> result = Result.create();
        result.setSuccess(false);
        result.setCode(code.getCode());
        result.setMessage(message);
        return result;
    }

    /**
     * 失败返回
     * @param code 错误码
     * @param message 错误提示消息
     * @return 返回值
     */
    public static Result<Object> fail(int code, String message) {
        Result<Object> result = Result.create();
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 错误，返回空数据
     * @param code
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> fail(int code, T data) {
        Result<T> result = Result.create();
        result.setSuccess(false);
        result.setCode(code);
        result.setData(data);
        return result;
    }
    /**
     * 失败返回
     * @param code 错误码
     * @return 返回值
     */
    public static Result<Object> fail(ErrorCode code) {
        Result<Object> result = Result.create();
        result.setSuccess(false);
        result.setCode(code.getCode());
        result.setMessage(code.getMessage());
        return result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
