package com.zr.test.demo.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SystemPasswordDTO {
    @ApiModelProperty(value = "旧密码", example = "saasasdaszxwer")
    @NotBlank(message="旧密码不能为空")
    private String password;
    @ApiModelProperty(value = "新密码", example = "saasasdaszxwer")
    @NotBlank(message="新密码不能为空")
    private String newPassword;

}
