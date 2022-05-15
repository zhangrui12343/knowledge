package com.zr.test.demo.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zr.test.demo.dao.IRoleDao;
import com.zr.test.demo.dao.IRoleMenuDao;
import com.zr.test.demo.model.entity.RoleEntity;
import com.zr.test.demo.model.entity.RoleMenuEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class RoleMenuDaoImpl {
    @Autowired
    private IRoleMenuDao dao;
    public int insertOne(RoleMenuEntity entity){
        return dao.insert(entity);
    }

    public List<RoleMenuEntity> selectByEntity(RoleMenuEntity entity){
        if (entity == null) {
            return dao.selectList(null);
        }
        QueryWrapper<RoleMenuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(entity);
        return dao.selectList(queryWrapper);
    }
    public int updateById(RoleMenuEntity entity){
        return dao.updateById(entity);
    }

    public int deleteByRoleId(RoleMenuEntity entity){
        QueryWrapper<RoleMenuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(entity);
        return dao.delete(queryWrapper);
    }

    public int insertBatchIgnore(List<RoleMenuEntity> list){
        return dao.insertBatchIgnore(list);
    }
}
