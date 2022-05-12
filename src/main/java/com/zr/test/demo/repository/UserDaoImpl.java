package com.zr.test.demo.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zr.test.demo.dao.IUserDao;
import com.zr.test.demo.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDaoImpl {
    @Autowired
    private IUserDao userDao;

    public int insertOne(UserEntity user){
        return userDao.insert(user);
    }

    public UserEntity selectByPassword(String username, String password){
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        UserEntity u=new UserEntity();
        u.setUsername(username);
        u.setUsername(password);
        queryWrapper.setEntity(u);
        return userDao.selectOne(queryWrapper);
    }

}
