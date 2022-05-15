package com.zr.test.demo.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zr.test.demo.dao.IUserDao;
import com.zr.test.demo.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDaoImpl {
    @Autowired
    private IUserDao userDao;
    public int insertOne(UserEntity user){
        return userDao.insert(user);
    }

    public List<UserEntity> selectByEntity(UserEntity entity){
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(entity);
        return userDao.selectList(queryWrapper);
    }
    public int updateById(UserEntity user){
        return userDao.updateById(user);
    }

    public int deleteById(Integer id){
        return userDao.deleteById(id);
    }

    public IPage<UserEntity> querySystem(Integer status, Integer userId,int page, int size) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",status);
        queryWrapper.ne("id",userId);
        queryWrapper.lt("role",2);
        queryWrapper.orderByDesc("id");
        return userDao.selectPage(new Page<>(page,size),queryWrapper);
    }

    public IPage<UserEntity> selectByPage(UserEntity entity, int page, int size,boolean desc,String column){
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(entity);
        if(desc){
            queryWrapper.orderByDesc(column);
        }else {
            queryWrapper.orderByAsc(column);
        }
        return userDao.selectPage(new Page<>(page,size),queryWrapper);
    }
}
