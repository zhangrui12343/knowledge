package com.zr.test.demo.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CourseListVO {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "课程名称")
    private String name;

    @ApiModelProperty(value = "授课老师")
    private String teacher;

    @ApiModelProperty(value = "图片")
    private Long img;

    @ApiModelProperty(value = "分类")
    private String category;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "观看量")
    private Long count;

    @ApiModelProperty(value = "标签")
    private List<String> tags;
    
    @ApiModelProperty(value = "上传时间")
    private String time;

    @ApiModelProperty(value = "是否精品")
    private Integer excellent;

}
