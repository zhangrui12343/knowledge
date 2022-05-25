package com.zr.test.demo.config.enums;

/**
 * 错误码定义
 *
 * @author yangliangliang
 * @date 2018/11/1
 */
public enum  ErrorCode {
    /**
     * 系统错误码和对应提示信息
     */
    SUCCESS(200,"成功"),
    SYS_BAD_REQUEST_ERR(201,"非法请求"),
    SYS_RUNTIME_ERR(202,"系统运行错误"),
    SYS_UNKNOWN_ERR(202,"系统未知异常"),
    SYS_CUSTOM_ERR(202,"系统异常，自定义提示异常"),
    SYS_NO_AUTHORITY(203,"没有权限"),
    SYS_CODE_ERRO(203,"验证码错误"),
    SYS_AUTHORITY_INVALID_ERR(204,"鉴权失败"),
    SYS_ACCOUNT_HAS_EXPIRED_ERR(205,"账号过期"),
    SYS_ACCOUNT_HAS_BANED_ERR(205,"账号被禁用"),
    SYS_USER_OR_PWD_ERROR_ERR(206,"用户名密码错误"),
    SYS_USERNAME_EXIST_ERROR_ERR(206,"用户名已存在"),
    SYS_PARAM_NUM_ERROR(207,"参数数量错误"),
    SYS_PARAM_ERR(208,"参数校验失败"),
    SYS_PARAM_INNER_ERR(208,"参数内部校验失败"),
    SYS_NOT_FIND_KEY(209,"key不存在"),
    SYS_KEY_ERROR(210,"key错误"),
    SYS_NO_USAABLE_DATA(212,"没有可用数据"),
    SYS_FILE_NOT_EXIST(215,"文件资源不存在"),
    SYS_DELETE_FIFLE_ERR(215,"删除文件出错"),

    SYS_UNDEFINED_ERR(216,"未定义异常"),
    SYS_NETWORK_ERR(217,"网络异常"),
    SYS_NONSUPPORT_TYPE(218,"不支持该操作"),
    SYS_LOCAL_TO_UTC_ERR(219, "本地时间转UTC失败"),
    SYS_UTC_TO_LOCAL_ERR(220, "UTC转本地时间失败"),
    SYS_ENCRIPT_ERR(221, "加密失败"),
    SYS_DECRIPT_ERR(222, "解密失败"),
    SYS_KEY_PARAM_ERR(223, "请求token key参数格式错误"),
    SYS_STARTTIME_GT_ENDTIME(224, "开始时间大于结束时间"),
    SYS_WRITE_FILE_ERR(225, "保存文件到磁盘出错"),
    SYS_REQUEST_HEADER_ERR(226, "请求头解析出错"),
    SYS_DYNAMIC_DATASOURCE_ERR(227, "切换动态数据源出错"),
    SYS_NO_DOWNLOAD_AUTH_ERR(228, "没有下载资源权限"),
    SYS_CREATE_FIFLE_ERR(229,"创建文件出错"),
    SYS_HANDLE_DATETIME_ERR(230, "处理时间出错"),
    SYS_APP_PUSH_ERROR(231, "APP推送错误"),
    SYS_GET_DISTRIBUTED_LOCK_TIMEOUT(232, "获取分布式锁超期"),
    SYS_QUART_ADD_JOB_ERR(233, "添加quart任务失败"),

    SYS_DATABASE_CONN_ERROR(300,"数据库连接错误"),
    SYS_DATABASE_OPT_ERROR(301,"数据操作失误"),
    SYS_INTERFACE_ERROR(302,"接口里面调用接口出错"),
    SYS_DATABASE_PRE_ERROR(303,"sql预编译出错"),
    SYS_DATABASE_PRE_SET_ERROR(304,"sql预编译，设置值出错"),
    SYS_START_UP_ERROR(305,"系统启动失败"),


    SEARCH_TERREC_FAIL(400,"查询错误"),
    FILE_UPLOAD_FAIL(401,"上传文件失败"),
    FILE_DELETE_FAIL(402,"文件删除失败"),
    EXEC_SQL_ERROR(412,"sql执行失败"),
    KEY_EXIST(416,"key已存在"),

    DATA_NOT_EXIST(425,"数据资源不存在"),
    PARSE_SQL_ERROR(428,"sql 查询解析失败"),


    ;
    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 根据错误码得到消息定义
     * @param code 错误码
     * @return 错误码对应提示
     */
    public static String getMsgByCode(Integer code) {
        String msg = "未知异常";
        if(code == null) {
            return msg;
        }
        for(ErrorCode errorCode : ErrorCode.values()) {
            if(code == errorCode.getCode()) {
                msg = errorCode.getMessage();
                break;
            }
        }
        return msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
