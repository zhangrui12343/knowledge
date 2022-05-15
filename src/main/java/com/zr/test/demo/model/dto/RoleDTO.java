package com.zr.test.demo.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RoleDTO {
    @ApiModelProperty(value = "角色id", example = "1")
    private Integer id;
    @ApiModelProperty(value = "角色名称", example = "asasas")
    @NotBlank(message="角色名称不能为空")
    private String name;
    @ApiModelProperty(value = "备注", example = "1213")
    private String memo;
    @ApiModelProperty(value = "页面权限", example = "[1]")
    private Integer[] menu;

}
