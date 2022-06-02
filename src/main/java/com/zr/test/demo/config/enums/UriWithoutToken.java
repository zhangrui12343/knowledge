package com.zr.test.demo.config.enums;

/**
 * 不用权限校验的接口  白名单
 * @author streamax-dev
 */

public enum UriWithoutToken {
    /**
     * 登录 注册 找回密码 验证码 资源文件获取
     */
    USER_LOGIN("/api/v1/user/login"),
    USER_REGISTER("/api/v1/user/register"),
    USER_FIND_PWD("/api/v1/user/findPassword"),
    USER_GET_MESSAGE_CODE("/api/v1/user/getCode"),
    FILE_VIEW("/api/v1/file/view"),
    FILE_UPLOADS("/api/v1/uploads"),
    SYS_USER_LOGIN("/api/v1/sys-user/login"),
    /**
     * 课程分类 课程类型  课程列表 课程详情
     */
    COURSE_CATEGORY("/api/v1/course-category/query"),
    COURSE_TYPE("/api/v1/course-type/query/"),
    COURSE_LIST("/api/v1/course/list"),
    COURSE_DETAIL("/api/v1/course/detail/"),
    /**
     * 专题列表 专题详情  专题大分类
     */
    FIRST_CATEGORY("/api/v1/first-category/list"),

    AFTER_LIST("/api/v1/after-course/list"),
    ATERT_DETAIL("/api/v1/after-course/detail/"),
    ATERT_MORE("/api/v1/after-course/listMore"),

    TOPIC_LIST("/api/v1/topic/list"),
    TOPIC_DETAIL("/api/v1/topic/detail/"),
    TOPIC_MORE("/api/v1/topic/listMore"),

    TEACHER_LIST("/api/v1/teacher-training/list"),
    TEACHER_DETAIL("/api/v1/teacher-training/detail/"),
    TEACHER_MORE("/api/v1/teacher-training/listMore"),
    /**
     * 信课融合
     */
    APP_TYPE_LIST("/api/v1/app-category/list"),
    APP_LIST("/api/v1/app/list"),
    APP_UNIVERSAL_LIST("/api/v1/app/universal/list"),
    APP_CASE_DETAIL("/api/v1/app-case/detail/"),
    TOOL_LIST("/api/v1/tool/list"),

    /**
     * 首页
     */
    INDEX("/api/v1/web/index"),
    SEARCH("/api/v1/web/search"),
    ;

    private String uri;
    UriWithoutToken(String uri){
        this.uri=uri;
    }

    public String getUri() {
        return uri;
    }

    public static boolean exist(String uri){
        for (UriWithoutToken enu:UriWithoutToken.values()){
            if(uri.startsWith(enu.getUri())){
                return true;
            }
        }
        return false;
    }
}
