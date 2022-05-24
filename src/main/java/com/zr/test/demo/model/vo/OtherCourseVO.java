package com.zr.test.demo.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "课后、专题、教师研修出参", description = "")
public class OtherCourseVO {
    @ApiModelProperty(value = "id", example = "1213")
    private Long id;

    @ApiModelProperty(value = "名称", example = "1213")
    private String name;

    @ApiModelProperty(value = "类型", example = "1213")
    private String type;

    @ApiModelProperty(value = "分类", example = "1213")
    private String category;

    @ApiModelProperty(value = "标签", example = "1213")
    private String tag;

    @ApiModelProperty(value = "上传时间", example = "1213")
    private String time;

    @ApiModelProperty(value = "上下架", example = "1213")
    private Integer status;
}
