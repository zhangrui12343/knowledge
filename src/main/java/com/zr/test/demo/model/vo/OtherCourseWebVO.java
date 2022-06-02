package com.zr.test.demo.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OtherCourseWebVO {
    @ApiModelProperty(value = "课后专题师研资源id", example = "1")
    private Long id;
    @ApiModelProperty(value = "封面", example = "1")
    private Long img;
    @ApiModelProperty(value = "课后专题师研资源标题", example = "标题")
    private String name;
    @ApiModelProperty(value = "课后专题师研资源描述", example = "描述")
    private String description;
    @ApiModelProperty(value = "观看量", example = "1213")
    private Long count;
}
