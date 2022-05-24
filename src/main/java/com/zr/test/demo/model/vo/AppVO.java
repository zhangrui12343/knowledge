package com.zr.test.demo.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AppVO {
    @ApiModelProperty(value = "id", example = "1213")
    private Long id;

    @ApiModelProperty(value = "软件名称", example = "1213")
    private String name;

    @ApiModelProperty(value = "log的地址", example = "1213")
    private String logo;

    @ApiModelProperty(value = "类型", example = "1213")
    private String type;

    @ApiModelProperty(value = "科目", example = "1213")
    private String subject;

    @ApiModelProperty(value = "平台", example = "1213")
    private String platform;

    @ApiModelProperty(value = "标签", example = "1213")
    private String tags;

    @ApiModelProperty(value = "上传时间", example = "1213")
    private String time;

    @ApiModelProperty(value = "上下架", example = "1213")
    private Integer status;

    @ApiModelProperty(value = "上下架", example = "1213")
    private Integer universal;


}
