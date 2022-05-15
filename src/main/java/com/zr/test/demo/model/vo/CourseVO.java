package com.zr.test.demo.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CourseVO {
    @ApiModelProperty(value = "id", example = "1213")
    private Long id;

    @ApiModelProperty(value = "课程名称", example = "1213")
    private String name;

    @ApiModelProperty(value = "学段", example = "1213")
    private String xueduan;

    @ApiModelProperty(value = "年级", example = "1213")
    private String grade;

    @ApiModelProperty(value = "学科", example = "1213")
    private String subject;

    @ApiModelProperty(value = "册次", example = "1213")
    private String books;
    @ApiModelProperty(value = "分类名", example = "1213")
    private String secondCategoryName;

    @ApiModelProperty(value = "标签名", example = "1213")
    private String tagName;

    @ApiModelProperty(value = "上传时间", example = "1213")
    private String time;

    @ApiModelProperty(value = "上下架", example = "1213")
    private Integer status;

    @ApiModelProperty(value = "图片的地址", example = "1213")
    private String img;

    @ApiModelProperty(value = "文档的地址", example = "1213")
    private String[] pdf;

    @ApiModelProperty(value = "视频的地址", example = "1213")
    private String video;
}
