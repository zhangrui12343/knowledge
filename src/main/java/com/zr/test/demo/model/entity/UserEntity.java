package com.zr.test.demo.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@TableName(value = "user")
public class UserEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String phone;

    private String register;
    @TableField(value = "lastlogin")
    private String lastLogin;

    private Integer status;

    private String password;

    private Integer student;

    private String school;

    private String studentNo;

    private Integer intranet;


}
