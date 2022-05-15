package com.zr.test.demo.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDTO {
    @ApiModelProperty(value = "用户名", example = "qwwqqw")
    @NotBlank(message="用户名不能为空")
    private String username;
    @ApiModelProperty(value = "密码", example = "saasasdaszxwer")
    @NotBlank(message="密码不能为空")
    private String password;
}
