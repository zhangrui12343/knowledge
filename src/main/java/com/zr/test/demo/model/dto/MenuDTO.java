package com.zr.test.demo.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MenuDTO {
    @ApiModelProperty(value = "菜单id", example = "1")
    private Integer id;
    @ApiModelProperty(value = "菜单名称", example = "asasas")
    private String name;
    @ApiModelProperty(value = "url", example = "asasas")
    private String url;
    @ApiModelProperty(value = "状态", example = "asasas")
    private Integer state;
    @ApiModelProperty(value = "备注", example = "1213")
    private String memo;
    @ApiModelProperty(value = "图标", example = "1213")
    private String icon;
    @ApiModelProperty(value = "父级id", example = "1213")
    private Integer pid;

}
