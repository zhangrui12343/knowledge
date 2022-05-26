package com.zr.test.demo.config.enums;

/**
 * 不用权限校验的接口
 * @author streamax-dev
 */

public enum UriWithoutToken {
    /**
     * uri
     */
    USER_LOGIN("/api/v1/user/login"),
    USER_REGISTER("/api/v1/user/register"),
    USER_FIND_PWD("/api/v1/user/findPassword"),
    USER_GET_MESSAGE_CODE("/api/v1/user/getCode"),
    FILE_VIEW("/api/v1/file/view"),
    SYS_USER_LOGIN("/api/v1/sys-user/login");

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
