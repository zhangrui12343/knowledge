package com.zr.test.demo.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleVO {
    @ApiModelProperty(value = "id", example = "1213")
    private Integer id;

    @ApiModelProperty(value = "角色名称", example = "1213")
    private String name;

    @ApiModelProperty(value = "备注", example = "1213")
    private String memo;

    @ApiModelProperty(value = "所属权限", example = "yyyy-MM-dd hh:mm:ss")
    private Integer[] menu;

}
