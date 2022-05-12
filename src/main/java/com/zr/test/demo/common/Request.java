package com.zr.test.demo.common;

/**
 * 基础请求结构
 *
 * @author llyang
 * @date 2018/10/17 10:39
 */
public class Request<T> extends BaseRequest{
    /**
     * 请求数据结构
     */
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Request() {
    }

    /**
     * 创建请求
     * @param data
     * @param appId
     * @param userId
     * @param userName
     * @param roleCode
     * @param lang
     * @param <T>
     * @return
     */
    public static <T> Request<T> create(T data, Integer appId, Integer userId, String userName, String roleCode, String lang) {
        Request<T> request = new Request<T>();
        request.setData(data);
        request.setAppId(appId);
        request.setUserId(userId);
        request.setUserName(userName);
        request.setRoleCode(roleCode);
        request.setLang(lang);
        return request;
    }

    /**
     * 创建请求
     * @param data
     * @param appId
     * @param userId
     * @param <T>
     * @return
     */
    public static <T> Request<T> create(T data, Integer appId, Integer userId) {
        return create(data,appId,userId,null,null,null);
    }

    /**
     * 创建请求
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Request<T> create(T data) {
        return Request.create(data,null,null);

    }

}
