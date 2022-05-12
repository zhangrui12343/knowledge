package com.zr.test.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zr.test.demo.model.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IUserDao extends BaseMapper<UserEntity> {

}
