package com.zr.test.demo.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateUserDTO {
    @ApiModelProperty(value = "id", example = "asasas")
    @NotNull(message = "主键id 不能为空")
    private Integer id;
    @ApiModelProperty(value = "昵称或者名字", example = "asasas")
    private String name;
    @ApiModelProperty(value = "手机号", example = "1213")
    private String phone;
    @ApiModelProperty(value = "密码", example = "saasasdaszxwer")
    private String password;
    @ApiModelProperty(value = "状态", example = "1")
    private Integer status;

}
