package com.zr.test.demo.model.vo;

import com.zr.test.demo.model.entity.App;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class WebAfterDetailVO {
    @ApiModelProperty(value = "id", example = "1213")
    private Long id;

    @ApiModelProperty(value = "课程名称", example = "1213")
    private String name;

    @ApiModelProperty(value = "图片的id", example = "1213")
    private Long img;

    @ApiModelProperty(value = "课程类型", example = "1213")
    private String type;

    @ApiModelProperty(value = "课程分类", example = "1213")
    private List<String> categories;

    @ApiModelProperty(value = "课程标签", example = "1213")
    private List<String> tags;


    @ApiModelProperty(value = "课程简介", example = "1213")
    private String description;
    @ApiModelProperty(value = "视频学习清单")
    private List<FileVO> videoList;
    @ApiModelProperty(value = "文档学习清单")
    private List<FileVO> docList;
    @ApiModelProperty(value = "上传时间", example = "1213")
    private Date time;

    @ApiModelProperty(value = "观看量", example = "1213")
    private Long count;
}
