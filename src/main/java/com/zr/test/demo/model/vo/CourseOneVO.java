package com.zr.test.demo.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CourseOneVO {
    @ApiModelProperty(value = "id", example = "1213")
    private Long id;

    @ApiModelProperty(value = "课程名称", example = "1213")
    private String name;

    @ApiModelProperty(value = "授课老师", example = "1213")
    private String teacher;

    @ApiModelProperty(value = "学段", example = "1213")
    private String xueduan;

    @ApiModelProperty(value = "年级", example = "1213")
    private String grade;

    @ApiModelProperty(value = "学科", example = "1213")
    private String subject;

    @ApiModelProperty(value = "册次", example = "1213")
    private String books;

    @ApiModelProperty(value = "图片的地址", example = "1213")
    private String img;

    @ApiModelProperty(value = "册次", example = "1213")
    private Long category;

    @ApiModelProperty(value = "册次", example = "1213")
    private Long type;

    @ApiModelProperty(value = "册次", example = "1213")
    private Long category;
    @ApiModelProperty(value = "上传时间", example = "1213")
    private String time;

    @ApiModelProperty(value = "上下架", example = "1213")
    private Integer status;


    @ApiModelProperty(value = "学习任务的地址", example = "1213")
    private String learningTask;

    @ApiModelProperty(value = "课后练习的地址", example = "1213")
    private String homework;

    @ApiModelProperty(value = "视频的地址", example = "1213")
    private String video;
}
