package com.zr.test.demo.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zr.test.demo.dao.IMenuDao;
import com.zr.test.demo.dao.IRoleDao;
import com.zr.test.demo.dao.IRoleMenuDao;
import com.zr.test.demo.model.entity.MenuEntity;
import com.zr.test.demo.model.entity.RoleEntity;
import com.zr.test.demo.model.entity.RoleMenuEntity;
import com.zr.test.demo.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleMenuDaoImpl {
    @Autowired
    private IRoleMenuDao dao;
    @Autowired
    private IMenuDao menuDao;
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
    public List<Integer> selectMenusByRoleId(Integer id){
        if(id<=2){
            List<MenuEntity> list=menuDao.selectList(null);
            return list.stream().map(MenuEntity::getId).collect(Collectors.toList());
        }
        QueryWrapper<RoleMenuEntity> queryWrapper = new QueryWrapper<>();
        RoleMenuEntity entity=new RoleMenuEntity();
        entity.setRoleId(id);
        queryWrapper.setEntity(entity);
        List<RoleMenuEntity> list=dao.selectList(queryWrapper);
        if(ListUtil.isEmpty(list)){
            return new ArrayList<>();
        }
        return list.stream().map(RoleMenuEntity::getMenuId).collect(Collectors.toList());
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
