package com.zr.test.demo.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserDTO {
    @ApiModelProperty(value = "是否是学生，1:学生，0：普通用户", example = "1")
    @NotNull(message="用户类型不能为空")
    private Integer student;

    @ApiModelProperty(value = "名字", example = "asasas")
    @NotBlank(message="名字不能为空")
    private String name;

    @ApiModelProperty(value = "手机号", example = "1213")
    private String phone;
    @ApiModelProperty(value = "密码", example = "saasasdaszxwer")
    private String password;

    @ApiModelProperty(value = "学校", example = "清华大学")
    private String school;

    @ApiModelProperty(value = "学籍号", example = "123456")
    private String studentNo;
}
