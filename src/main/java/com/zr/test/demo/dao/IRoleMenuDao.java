package com.zr.test.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zr.test.demo.model.entity.RoleEntity;
import com.zr.test.demo.model.entity.RoleMenuEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface IRoleMenuDao extends BaseMapper<RoleMenuEntity> {

    @Insert("<script>" +
            "insert ignore into list(role_id,menu_id) values " +
            " <foreach collection =\"list\" item=\"item\" index= \"index\" separator =\",\">" +
            "   (" +
            "     #{item.roleId}, " +
            "     #{item.menu_id}" +
            "    )" +
            " </foreach >" +
            "</script>")
    int insertBatchIgnore(List<RoleMenuEntity> list);
}
