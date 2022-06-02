package com.zr.test.demo.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zr.test.demo.common.Constant;
import com.zr.test.demo.dao.ISysUserDao;
import com.zr.test.demo.dao.IUserDao;
import com.zr.test.demo.model.entity.SysUserEntity;
import com.zr.test.demo.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SysUserDaoImpl {
    @Autowired
    private ISysUserDao userDao;
    public int insertOne(SysUserEntity user){
        return userDao.insert(user);
    }

    public List<SysUserEntity> selectByEntity(SysUserEntity entity){
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(entity);
        return userDao.selectList(queryWrapper);
    }
    public int updateById(SysUserEntity user){
        return userDao.updateById(user);
    }
    public SysUserEntity selectById(Integer id){
        return userDao.selectById(id);
    }
    public int deleteById(Long id){
        return userDao.deleteById(id);
    }

    public Page<SysUserEntity> querySystem(Integer status, Integer userId, int page, int size) {
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<>();
        if(status!=null){
            queryWrapper.eq("status",status);
        }
        queryWrapper.ne("id",userId);
        queryWrapper.orderByDesc("id");
        return PageHelper.startPage(page,size).doSelectPage(()->userDao.selectList(queryWrapper));
    }

    public boolean checkUser(Integer userId) {
        SysUserEntity userEntity= this.userDao.selectById(userId);
        return userEntity != null && userEntity.getStatus() != 0;
    }
//
//    public IPage<UserEntity> selectByPage(UserEntity entity, int page, int size,boolean desc,String column){
//        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
//        queryWrapper.setEntity(entity);
//        if(desc){
//            queryWrapper.orderByDesc(column);
//        }else {
//            queryWrapper.orderByAsc(column);
//        }
//        return userDao.selectPage(new Page<>(page,size),queryWrapper);
//    }
}
