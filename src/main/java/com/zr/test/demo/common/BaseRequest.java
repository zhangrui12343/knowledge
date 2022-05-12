package com.zr.test.demo.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 基础请求结构
 *
 * @author llyang
 * @date 2018/10/17 10:39
 */
@Data
public class BaseRequest implements Serializable {
    /**
     * 唯一标志
     */
    protected String sid;
    /**
     * 应用ID
     */
    protected Integer appId;
    /**
     * 用户ID
     */
    protected Integer userId;
    /**
     * 请求语言
     */
    protected String lang;
    /**
     * 用户名
     */
    protected String userName;
    /**
     * 角色编码
     */
    protected String roleCode;
}
