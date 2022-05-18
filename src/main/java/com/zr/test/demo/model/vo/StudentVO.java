package com.zr.test.demo.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StudentVO {
    @ApiModelProperty(value = "id", example = "1213")
    private Integer id;
    @ApiModelProperty(value = "昵称", example = "1213")
    private String school;
    @ApiModelProperty(value = "姓名", example = "1213")
    private String name;
    @ApiModelProperty(value = "学籍号", example = "1213")
    private String studentNo;
    @ApiModelProperty(value = "注册时间", example = "yyyy-MM-dd hh:mm:ss")
    private String register;
    @ApiModelProperty(value = "上次登录时间", example = "yyyy-MM-dd hh:mm:ss")
    private String lastLogin;
    @ApiModelProperty(value = "是否为内网学生", example = "1")
    private Integer intranet;
    @ApiModelProperty(value = "状态", example = "1")
    private Integer status;
}
