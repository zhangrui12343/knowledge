package com.zr.test.demo.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CourseOneVO {
    @ApiModelProperty(value = "id", example = "1213")
    private Long id;
    @ApiModelProperty(value = "课程名称", example = "1213")
    private String name;
    @ApiModelProperty(value = "授课老师", example = "1213")
    private String teacher;
    @ApiModelProperty(value = "图片的地址", example = "1213")
    private String img;
    @ApiModelProperty(value = "图片文件id", example = "1213")
    private Long imgId;
    @ApiModelProperty(value = "课程分类", example = "1213")
    private List<Long> category;
    @ApiModelProperty(value = "课程类型", example = "1213")
    private List<Long>  courseTypeId;
    @ApiModelProperty(value = "课程标签", example = "1213")
    private List<Long> courseTag;
    @ApiModelProperty(value = "相关应用", example = "1213")
    private List<Long> app;
    @ApiModelProperty(value = "课程详情", example = "1213")
    private String description;
    @ApiModelProperty(value = "学习任务的地址", example = "1213")
    private String learningTask;
    @ApiModelProperty(value = "学习任务文件id", example = "1213")
    private Long learningTaskId;
    @ApiModelProperty(value = "课后练习的地址", example = "1213")
    private String homework;
    @ApiModelProperty(value = "课后练习文件id", example = "1213")
    private Long homeworkId;
    @ApiModelProperty(value = "视频的地址", example = "1213")
    private String video;
    @ApiModelProperty(value = "视频文件id", example = "1213")
    private Long videoId;
    @ApiModelProperty(value = "是否精品课程", example = "1213")
    private Integer excellent;
    @ApiModelProperty(value = "上下架", example = "1213")
    private Integer status;
    @ApiModelProperty(value = "上传时间", example = "1213")
    private Date time;
}
