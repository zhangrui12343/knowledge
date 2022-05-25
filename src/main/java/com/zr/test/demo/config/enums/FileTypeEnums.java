package com.zr.test.demo.config.enums;

/**
 * 文件类型枚举
 * @author streamax-dev
 */

public enum FileTypeEnums {
    /**
     * uri
     */
    QUERY1("image/png",".png"),
    QUERY2("image/jpeg",".jpeg"),
    QUERY3("image/jpeg",".jpg"),
    QUERY4("image/jpeg",".jpe"),
    QUERY5("image/x-icon",".ico"),
    QUERY6("application/x-ico",".ico"),
    QUERY7("image/gif",".gif"),
    QUERY9("application/pdf",".pdf"),
    QUERY12("application/msword",".doc"),
    QUERY11("application/msword",".docx"),
    QUERY13("application/x-xls",".xls"),
    QUERY10("video/mpeg4",".mp4");

    private String contentType;
    private String suffix;
    FileTypeEnums(String contentType,String suffix){
        this.contentType=contentType;
        this.suffix=suffix;
    }

    public String getContentType() {
        return this.contentType;
    }
    public String getSuffix() {
        return this.suffix;
    }
    public static String endWith(String suffix){
        suffix=suffix.substring(suffix.lastIndexOf("."));
        for (FileTypeEnums enu: FileTypeEnums.values()){
            if(enu.getContentType().equals(suffix)){
                return enu.getSuffix();
            }
        }
        return "";
    }
}
