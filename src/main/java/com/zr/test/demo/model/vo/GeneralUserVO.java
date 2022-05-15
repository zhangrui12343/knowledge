package com.zr.test.demo.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GeneralUserVO {
    @ApiModelProperty(value = "id", example = "1213")
    private Integer id;

    @ApiModelProperty(value = "昵称", example = "1213")
    private String name;

    @ApiModelProperty(value = "手机号", example = "1213")
    private String phone;

    @ApiModelProperty(value = "注册时间", example = "yyyy-MM-dd hh:mm:ss")
    private String register;

    @ApiModelProperty(value = "上次登录时间", example = "yyyy-MM-dd hh:mm:ss")
    private String lastLogin;

    @ApiModelProperty(value = "状态", example = "1")
    private Integer status;
}
