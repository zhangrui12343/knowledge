package com.zr.test.demo.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CourseVO {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "课程名称")
    private String name;

    @ApiModelProperty(value = "授课老师")
    private String teacher;

    @ApiModelProperty(value = "类型")
    private List<String> category;

    @ApiModelProperty(value = "分类")
    private List<String> type;

    @ApiModelProperty(value = "标签")
    private List<String> tags;
    
    @ApiModelProperty(value = "上传时间")
    private String time;

    @ApiModelProperty(value = "上下架")
    private Integer status;

}
