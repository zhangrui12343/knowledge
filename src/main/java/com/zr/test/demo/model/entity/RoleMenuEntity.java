package com.zr.test.demo.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "role_menu")
public class RoleMenuEntity {

    private Integer roleId;

    private Integer menuId;

    public RoleMenuEntity(Integer roleId, Integer menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }

    public RoleMenuEntity() {
    }
}
