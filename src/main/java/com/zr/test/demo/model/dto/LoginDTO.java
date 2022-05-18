package com.zr.test.demo.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDTO {
    @ApiModelProperty(value = "是否是学生", example = "1")
    @NotBlank(message="类型不能为空")
    private Integer student;
    @ApiModelProperty(value = "手机号", example = "13444444444")
    private String phone;
    @ApiModelProperty(value = "密码", example = "saasasdaszxwer")
    private String password;
    @ApiModelProperty(value = "姓名", example = "张三")
    private String name;
    @ApiModelProperty(value = "学籍号", example = "123123123")
    private String studentNo;

}
