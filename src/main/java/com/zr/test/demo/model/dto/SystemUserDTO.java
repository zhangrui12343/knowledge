package com.zr.test.demo.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SystemUserDTO {
    @ApiModelProperty(value = "id", example = "1")
    private Integer id;
    @ApiModelProperty(value = "名字", example = "asasas")
    @NotBlank(message="名字不能为空")
    private String name;
    @ApiModelProperty(value = "用户名", example = "qwwqqw")
    @NotBlank(message="用户名不能为空")
    private String username;
    @ApiModelProperty(value = "密码", example = "saasasdaszxwer")
    private String password;
    @ApiModelProperty(value = "权限id", example = "1")
    @NotNull(message="权限id不能为空")
    private Integer role;
    @ApiModelProperty(value = "状态", example = "1")
    private Integer status;
}
