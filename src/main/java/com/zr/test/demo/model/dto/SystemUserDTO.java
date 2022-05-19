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
    private String name;
    @ApiModelProperty(value = "用户名", example = "qwwqqw")
    private String username;
    @ApiModelProperty(value = "密码", example = "saasasdaszxwer")
    private String password;
    @ApiModelProperty(value = "权限id", example = "1")
    private Integer role;
    @ApiModelProperty(value = "状态", example = "1")
    private Integer status;
}
