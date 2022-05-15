package com.zr.test.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zr.test.demo.model.entity.MenuEntity;
import com.zr.test.demo.model.entity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IMenuDao extends BaseMapper<MenuEntity> {

}
