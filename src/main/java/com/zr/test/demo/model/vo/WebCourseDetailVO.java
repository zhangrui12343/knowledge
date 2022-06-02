package com.zr.test.demo.model.vo;

import com.zr.test.demo.model.entity.App;
import com.zr.test.demo.model.entity.FileRouter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class WebCourseDetailVO {
    @ApiModelProperty(value = "id", example = "1213")
    private Long id;

    @ApiModelProperty(value = "课程名称", example = "1213")
    private String name;

    @ApiModelProperty(value = "授课老师", example = "1213")
    private String teacher;

    @ApiModelProperty(value = "图片的id", example = "1213")
    private Long img;

    @ApiModelProperty(value = "课程分类", example = "1213")
    private String category;

    @ApiModelProperty(value = "课程类型", example = "1213")
    private String courseType;

    @ApiModelProperty(value = "课程标签", example = "1213")
    private List<String> courseTag;

    @ApiModelProperty(value = "相关应用", example = "1213")
    private List<App> app;

    @ApiModelProperty(value = "课程详情", example = "1213")
    private String description;

    @ApiModelProperty(value = "视频")
    private Long video;
    @ApiModelProperty(value = "学习任务")
    private Long learningTask;
    @ApiModelProperty(value = "作业")
    private Long homework;

    @ApiModelProperty(value = "是否精品课程", example = "1213")
    private Integer excellent;
    @ApiModelProperty(value = "观看量", example = "1213")
    private Long count;

    @ApiModelProperty(value = "上传时间", example = "1213")
    private Date time;


}
