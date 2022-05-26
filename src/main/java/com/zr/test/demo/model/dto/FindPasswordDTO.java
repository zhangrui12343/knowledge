package com.zr.test.demo.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FindPasswordDTO {
    @ApiModelProperty(value = "手机号", example = "13444444444")
    @NotBlank(message = "手机号不能为空")
    private String phone;
    @ApiModelProperty(value = "新密码", example = "13444444444")
    @NotBlank(message = "密码不能为空")
    private String password;
    @ApiModelProperty(value = "验证码", example = "13444444444")
    @NotBlank(message = "验证码不能为空")
    private String code;
}
