package com.zr.test.demo.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zr.test.demo.dao.IRoleDao;
import com.zr.test.demo.dao.IUserDao;
import com.zr.test.demo.model.entity.RoleEntity;
import com.zr.test.demo.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class RoleDaoImpl {
    @Autowired
    private IRoleDao userDao;
    public int insertOne(RoleEntity role){
        return userDao.insert(role);
    }

    public List<RoleEntity> selectAll(){
        return userDao.selectList(null);
    }
    public int updateById(RoleEntity role){
        return userDao.updateById(role);
    }

    public int deleteById(Integer id){
        return userDao.deleteById(id);
    }


}
