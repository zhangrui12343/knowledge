package com.zr.test.demo.config.enums;

/**
 * 普通用户可以访问的接口
 * @author streamax-dev
 */

public enum GUserUri {
    /**
     * uri
     */
    QUERY0("/api/v1/course/query"),
    QUERY1("/api/v1/course-category/query"),
    QUERY2("/api/v1/course-tag/query"),
    QUERY3("/api/v1/course-type/query"),
    QUERY4("/api/v1/topic/query"),
    QUERY5("/api/v1/teacher-training/query"),
    QUERY6("/api/v1/first-category/query"),
    QUERY7("/api/v1/tag/query"),
    QUERY8("/api/v1/second-category/query"),
    QUERY9("/api/v1/app-category/query"),
    QUERY10("/api/v1/app/query"),
    QUERY11("/api/v1/app-case/query"),
    QUERY12("/api/v1/tool/query"),
    QUERY13("/api/v1/front"),
    QUERY14("/api/v1/after-course/query");

    private String uri;
    GUserUri(String uri){
        this.uri=uri;
    }

    public String getUri() {
        return uri;
    }

    public static boolean startWith(String uri){
        for (GUserUri enu: GUserUri.values()){
            if(uri.startsWith(enu.getUri())){
                return true;
            }
        }
        return false;
    }
}
