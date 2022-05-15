package com.zr.test.demo.service.impl;

import com.zr.test.demo.common.Constant;
import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.model.dto.*;
import com.zr.test.demo.model.entity.RoleEntity;
import com.zr.test.demo.model.entity.RoleMenuEntity;
import com.zr.test.demo.model.pojo.AuthKey;
import com.zr.test.demo.model.vo.RoleVO;
import com.zr.test.demo.repository.RoleDaoImpl;
import com.zr.test.demo.repository.RoleMenuDaoImpl;
import com.zr.test.demo.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoleServiceImpl implements IRoleService {
    private final RoleDaoImpl roleDao;
    private final RoleMenuDaoImpl roleMenuDao;

    @Autowired
    public RoleServiceImpl(RoleDaoImpl roleDao, RoleMenuDaoImpl roleMenuDao) {
        this.roleDao = roleDao;
        this.roleMenuDao = roleMenuDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Object> add(RoleDTO dto, HttpServletRequest request) {
        String token = request.getHeader(Constant.TOKEN);
        AuthKey authKey = (AuthKey) request.getSession().getAttribute(token);
        if (authKey.getRoleId() > Constant.ROLE_GENERAL_ADMIN) {
            throw new CustomException(ErrorCode.EVIDENCE_UNLOCK_AUTH);
        }
        RoleEntity entity = new RoleEntity();
        entity.setName(dto.getName());
        entity.setMemo(dto.getMemo());
        int i = roleDao.insertOne(entity);
        return getObjectResult(dto, entity, i);
    }

    @Override
    public Result<List<RoleVO>> query(HttpServletRequest request) {
        String token = request.getHeader(Constant.TOKEN);
        AuthKey authKey = (AuthKey) request.getSession().getAttribute(token);
        if (authKey.getRoleId() > Constant.ROLE_GENERAL_ADMIN) {
            throw new CustomException(ErrorCode.EVIDENCE_UNLOCK_AUTH);
        }
        List<RoleMenuEntity> all = roleMenuDao.selectByEntity(null);
        Map<Integer, List<Integer>> map = new HashMap<>();
        all.forEach(e -> {
            if (map.containsKey(e.getRoleId())) {
                map.get(e.getRoleId()).add(e.getMenuId());
            } else {
                List<Integer> l = new ArrayList<>();
                l.add(e.getMenuId());
                map.put(e.getRoleId(), l);
            }
        });
        List<RoleVO> list = new ArrayList<>();
        roleDao.selectAll().forEach(e -> {
            RoleVO v = new RoleVO();
            v.setId(e.getId());
            v.setName(e.getName());
            v.setMemo(e.getMemo());
            v.setMenu((Integer[]) map.get(e.getId()).toArray());
            list.add(v);
        });
        return Result.success(list);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Object> update(RoleDTO dto, HttpServletRequest request) {
        if(dto.getId()==null){
            throw new CustomException(ErrorCode.SYS_PARAM_ERR);
        }
        String token = request.getHeader(Constant.TOKEN);
        AuthKey authKey = (AuthKey) request.getSession().getAttribute(token);
        if (authKey.getRoleId() > Constant.ROLE_GENERAL_ADMIN) {
            throw new CustomException(ErrorCode.EVIDENCE_UNLOCK_AUTH);
        }
        RoleEntity entity = new RoleEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setMemo(dto.getMemo());
        int i = roleDao.updateById(entity);
        RoleMenuEntity roleMenuEntity=new RoleMenuEntity();
        roleMenuEntity.setRoleId(dto.getId());
        int j=roleMenuDao.deleteByRoleId(roleMenuEntity);
        if(j>=0){
            return getObjectResult(dto, entity, i);
        }
        return Result.fail(ErrorCode.SYS_UNKNOWN_ERR);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Object> delete(Integer id, HttpServletRequest request) {
        String token = request.getHeader(Constant.TOKEN);
        AuthKey authKey = (AuthKey) request.getSession().getAttribute(token);
        if (authKey.getRoleId() > Constant.ROLE_GENERAL_ADMIN) {
            throw new CustomException(ErrorCode.EVIDENCE_UNLOCK_AUTH);
        }
        int i=roleDao.deleteById(id);
        if(i>=0){
            RoleMenuEntity roleMenuEntity=new RoleMenuEntity();
            roleMenuEntity.setRoleId(id);
            int j=roleMenuDao.deleteByRoleId(roleMenuEntity);
            if(j>=0){
                log.info("删除角色权限关联表 {} 条",j);
            }
        }
        return Result.success(i);
    }


    private Result<Object> getObjectResult(RoleDTO dto, RoleEntity entity, int i) {
        List<RoleMenuEntity> list = new ArrayList<>();
        for (Integer mid : dto.getMenu()) {
            RoleMenuEntity e = new RoleMenuEntity(entity.getId(), mid);
            list.add(e);
        }
        roleMenuDao.insertBatchIgnore(list);
        return Result.success(i);
    }
}
