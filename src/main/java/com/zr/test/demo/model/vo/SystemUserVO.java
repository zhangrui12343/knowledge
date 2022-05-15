package com.zr.test.demo.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SystemUserVO {
    @ApiModelProperty(value = "id", example = "1213")
    private Integer id;
    @ApiModelProperty(value = "用户名", example = "1213")
    private String username;
    @ApiModelProperty(value = "名字", example = "1213")
    private String name;

    @ApiModelProperty(value = "角色id", example = "1213")
    private Integer role;

    @ApiModelProperty(value = "角色名字", example = "1213")
    private String roleName;

    @ApiModelProperty(value = "状态", example = "1")
    private Integer status;
}
