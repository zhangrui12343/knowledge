package com.zr.test.demo.service.impl;

import com.zr.test.demo.common.Constant;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.model.dto.MenuDTO;
import com.zr.test.demo.model.dto.RoleDTO;
import com.zr.test.demo.model.entity.MenuEntity;
import com.zr.test.demo.model.entity.RoleEntity;
import com.zr.test.demo.model.entity.RoleMenuEntity;
import com.zr.test.demo.model.pojo.AuthKey;
import com.zr.test.demo.model.vo.MenuVO;
import com.zr.test.demo.model.vo.RoleVO;
import com.zr.test.demo.repository.MenuDaoImpl;
import com.zr.test.demo.repository.RoleDaoImpl;
import com.zr.test.demo.repository.RoleMenuDaoImpl;
import com.zr.test.demo.service.IMenuService;
import com.zr.test.demo.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MenuServiceImpl implements IMenuService {
    private final MenuDaoImpl menuDao;
    private final RoleMenuDaoImpl roleMenuDao;

    @Autowired
    public MenuServiceImpl(MenuDaoImpl menuDao, RoleMenuDaoImpl roleMenuDao) {
        this.menuDao = menuDao;
        this.roleMenuDao = roleMenuDao;
    }


    @Override
    public Result<Object> add(MenuDTO dto, HttpServletRequest request) {
        MenuEntity entity=new MenuEntity();
        BeanUtils.copyProperties(dto, entity);
        return Result.success(menuDao.insertOne(entity));
    }

    @Override
    public Result<List<MenuVO>> query(HttpServletRequest request) {
        List<MenuVO> vos=new ArrayList<>();
        menuDao.selectByEntity(null).forEach(o->{
            MenuVO vo=new MenuVO();
            BeanUtils.copyProperties(o, vo);
            vos.add(vo);
        });
        return Result.success(vos);
    }

    @Override
    public Result<Object> update(MenuDTO dto, HttpServletRequest request) {
        if(dto.getId()==null){
            throw new CustomException(ErrorCode.SYS_PARAM_ERR);
        }
        MenuEntity entity=new MenuEntity();
        BeanUtils.copyProperties(dto, entity);
        return Result.success(menuDao.updateById(entity));
    }

    @Override
    public Result<Object> delete(Integer id, HttpServletRequest request) {
        int i=menuDao.deleteById(id);
        if(i>=0){
            RoleMenuEntity roleMenuEntity=new RoleMenuEntity();
            roleMenuEntity.setMenuId(id);
            int j=roleMenuDao.deleteByRoleId(roleMenuEntity);
            if(j>=0){
                log.info("删除角色权限关联表 {} 条",j);
            }
        }
        return Result.success(i);
    }
}
