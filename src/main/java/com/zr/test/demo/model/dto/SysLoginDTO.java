package com.zr.test.demo.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SysLoginDTO {
    @ApiModelProperty(value = "密码", example = "Aa111111")
    @NotBlank(message = "密码不能为空")
    private String password;
    @ApiModelProperty(value = "用户名", example = "useradmin")
    @NotBlank(message = "用户名不能为空")
    private String username;

}
