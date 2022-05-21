package com.zr.test.demo.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AfterCourseVO {
    @ApiModelProperty(value = "id", example = "1213")
    private Long id;

    @ApiModelProperty(value = "课后服务名称", example = "1213")
    private String name;

    @ApiModelProperty(value = "授课类型", example = "1213")
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
