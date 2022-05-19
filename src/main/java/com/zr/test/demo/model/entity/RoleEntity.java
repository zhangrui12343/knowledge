package com.zr.test.demo.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "role")
public class RoleEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String memo;

}
