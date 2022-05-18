package com.zr.test.demo.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FindPasswordDTO {
    @ApiModelProperty(value = "手机号", example = "13444444444")
    @NotBlank
    private String phone;
    @ApiModelProperty(value = "新密码", example = "13444444444")
    @NotBlank
    private String password;
    @ApiModelProperty(value = "验证码", example = "13444444444")
    @NotBlank
    private String code;
}
