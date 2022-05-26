package com.zr.test.demo.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zr.test.demo.common.Constant;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.dao.IUserDao;
import com.zr.test.demo.model.dto.GeneralUserDTO;
import com.zr.test.demo.model.dto.StudentDTO;
import com.zr.test.demo.model.entity.UserEntity;
import com.zr.test.demo.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDaoImpl {
    @Autowired
    private IUserDao userDao;

    public int insertOne(UserEntity user) {
        return userDao.insert(user);
    }

    public List<UserEntity> selectByEntity(UserEntity entity) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(entity);
        return userDao.selectList(queryWrapper);
    }

    public int updateById(UserEntity user) {
        return userDao.updateById(user);
    }

    public int deleteById(Integer id) {
        return userDao.deleteById(id);
    }


    public Page<UserEntity> selectByPage(StudentDTO user) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student", 1);
        if (!StringUtil.isEmpty(user.getName())) {
            queryWrapper.like("name", user.getName()).or().like("student_no", user.getName());
        }
        queryWrapper.orderByDesc("register");
        return PageHelper.startPage(user.getPage(), user.getPageSize())
                .doSelectPage( () -> this.userDao.selectList(queryWrapper));
    }

    public Page<UserEntity> selectByPage(GeneralUserDTO user) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student", 0);
        if (!StringUtil.isEmpty(user.getPhone())) {
            queryWrapper.like("phone", user.getPhone());
        }
        queryWrapper.orderByDesc("register");
        return PageHelper.startPage(user.getPage(), user.getPageSize())
                .doSelectPage( () -> this.userDao.selectList(queryWrapper));
    }
}
