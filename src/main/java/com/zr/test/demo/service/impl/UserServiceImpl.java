package com.zr.test.demo.service.impl;

import com.zr.test.demo.model.entity.UserEntity;
import com.zr.test.demo.repository.UserDaoImpl;
import com.zr.test.demo.service.IUserService;
import com.zr.test.demo.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserDaoImpl userDao;


    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void insert()  {
        try {
            insertRequired1();
        }catch (Exception e){

        }
        insertRequired2();

    }

    @Override
    public UserEntity getUserByPass(String username, String password) {
        //密文
        return userDao.selectByPassword(username,password);
    }

    public void insertRequired1()  {
        UserEntity user=new UserEntity();
        user.setName("张三"+ RandomUtil.getRandomInt(10));
        userDao.insertOne(user);
        throw new RuntimeException();
    }
    public void insertRequired2() {
        UserEntity user=new UserEntity();
        user.setName("李四"+ RandomUtil.getRandomInt(10));
        userDao.insertOne(user);
    }
}
