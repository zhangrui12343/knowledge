package com.zr.test.demo.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 返回结果基本类
 *
 * @author llyang
 * @date 2018/10/12 10:50
 */
@Data
public class BaseResult implements Serializable {
    /**
     * 是否成功
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected Boolean success;

    /**
     * 接口耗时（毫秒）
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected Long time;

    /**
     * 错误码code
     */
    @JsonProperty("errorcode")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected Integer code;
    /**
     * 错误提示信息
     */
    @JsonProperty("errorcase")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected String message;
    /**
     * 请求唯一标识
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected String sid;

    /**
     * 分页时候的总数据条数
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected Long total;

    /**
     * 分页时候当前页码
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected Integer page;

    /**
     * 分页时候的每页条数
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("pagesize")
    protected Integer pageSize;

    /**
     * 分页的总页数
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected Integer pages;


}
