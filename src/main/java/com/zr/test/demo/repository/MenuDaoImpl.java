package com.zr.test.demo.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zr.test.demo.dao.IMenuDao;
import com.zr.test.demo.model.entity.MenuEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class MenuDaoImpl {
    @Autowired
    private IMenuDao dao;
    public int insertOne(MenuEntity entity){
        return dao.insert(entity);
    }

    public List<MenuEntity> selectByEntity(MenuEntity entity){
        if(entity==null){
            return dao.selectList(null);
        }
        QueryWrapper<MenuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(entity);
        return dao.selectList(queryWrapper);
    }
    public int updateById(MenuEntity role){
        return dao.updateById(role);
    }

    public int deleteById(Integer id){
        return dao.deleteById(id);
    }


}
