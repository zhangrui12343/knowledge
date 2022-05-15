package com.zr.test.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zr.test.demo.common.Constant;
import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.model.dto.*;
import com.zr.test.demo.model.entity.UserEntity;
import com.zr.test.demo.model.pojo.AuthKey;
import com.zr.test.demo.model.vo.GeneralUserVO;
import com.zr.test.demo.model.vo.SystemUserVO;
import com.zr.test.demo.repository.UserDaoImpl;
import com.zr.test.demo.service.IUserService;
import com.zr.test.demo.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {
    private final UserDaoImpl userDao;

    @Autowired
    public UserServiceImpl(UserDaoImpl userDao) {
        this.userDao = userDao;
    }


    @Override
    public Result<Object> register(UserDTO user) {
        UserEntity userEntity=new UserEntity();
        //判断用户名是否存在
        userEntity.setUsername(user.getUsername());
        List<UserEntity> list = userDao.selectByEntity(userEntity);
        if(!ListUtil.isEmpty(list)){
            //用户名已存在
            return Result.fail(ErrorCode.SYS_USERNAME_EXIST_ERROR_ERR,"用户名已存在");
        }
        userEntity.setName(user.getName());
        userEntity.setPhone(user.getPhone());
        userEntity.setStatus(1);
        userEntity.setPassword(Md5Util.getMD5(user.getPassword()));
        userEntity.setRegister(TimeUtil.getTime());
        userEntity.setRole(Constant.ROLE_GENERAL_USER);
        userDao.insertOne(userEntity);
        return Result.success("注册成功");
    }

    @Override
    public Result<Object> login(LoginDTO loginDTO, HttpServletRequest request) {
        UserEntity userEntity=new UserEntity();
        //判断用户名是否存在
        userEntity.setUsername(loginDTO.getUsername());
        List<UserEntity> list = userDao.selectByEntity(userEntity);
        if(ListUtil.isEmpty(list)){
            //用户名不存在
            return Result.fail(ErrorCode.SYS_USERNAME_EXIST_ERROR_ERR,"用户名不存在");
        }
        if(list.size()>1){
            throw new CustomException(ErrorCode.SEARCH_TERREC_FAIL,"查询错误，请联系管理员");
        }
        UserEntity user=list.get(0);
        if(!user.getPassword().equals(Md5Util.getMD5(loginDTO.getPassword()))){
            return Result.fail(ErrorCode.SYS_USER_OR_PWD_ERROR_ERR);
        }
        if(user.getStatus()==0){
            return Result.fail(ErrorCode.SYS_ACCOUNT_HAS_BANED_ERR);
        }
        //登录成功后的操作
        user.setLastLogin(TimeUtil.getTime());
        userDao.updateById(user);
        String token;
        try {
            token = DESUtils.encrypt(DESUtils.KEY_DEFALUT, MessageFormat.format( "{0}|{1}|{2}",user.getRole(),user.getId(), TimeUtil.getTime()));
        } catch (Exception e) {
            throw new CustomException(ErrorCode.SYS_ENCRIPT_ERR, e.getMessage());
        }
        if (token == null) {
            throw new CustomException(ErrorCode.SYS_PARAM_INNER_ERR, "des encrypt key = null");
        }
        try {
            token = URLEncoder.encode(token, "utf-8");
        } catch (Exception e) {
            log.error("key des ulrencode error ：" + e.getMessage(), e);
            throw new CustomException(ErrorCode.SYS_PARAM_INNER_ERR, "key urlencode error");
        }
        AuthKey authKey=new AuthKey();
        authKey.setRoleId(user.getRole());
        authKey.setUserId(user.getId());
        authKey.setTime(TimeUtil.getTime());
        request.getSession().setAttribute(token,authKey);
        return Result.success(token);

    }


    @Override
    public Result<Object> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return Result.success(null);
    }

    @Override
    public Result<PageInfo<GeneralUserVO>> queryGeneral(GeneralUserDTO user, HttpServletRequest request) {
        String token=request.getHeader(Constant.TOKEN);
        AuthKey authKey= (AuthKey)request.getSession().getAttribute(token);
        if(authKey.getRoleId()>Constant.ROLE_CUSTOMER_SERVICE){
            throw new CustomException(ErrorCode.EVIDENCE_UNLOCK_AUTH);
        }
        UserEntity userEntity=new UserEntity();
        if(!StringUtil.isEmpty(user.getPhone())){
            userEntity.setPhone(user.getPhone());
        }
        userEntity.setStatus(user.getStatus());
        IPage<UserEntity> page=userDao.selectByPage(userEntity,user.getPage(),user.getPageSize(),true,"register");
        List<UserEntity> list=page.getRecords();
        long total=page.getTotal();
        list=ListUtil.page(list, user.getPage(), user.getPageSize());
        List<GeneralUserVO> res=new ArrayList<>(list.size());
        list.forEach(l->{
            GeneralUserVO vo=new GeneralUserVO();
            vo.setId(l.getId());
            vo.setLastLogin(l.getLastLogin());
            vo.setName(l.getName());
            vo.setPhone(l.getPhone());
            vo.setRegister(l.getRegister());
            vo.setStatus(l.getId());
            res.add(vo);
        });
        PageInfo<GeneralUserVO> pageInfo = new PageInfo<>();
        pageInfo.setTotal(ListUtil.isEmpty(res) ? 0 : total);
        pageInfo.setList(res);
        pageInfo.setPage(user.getPage());
        pageInfo.setPageSize(user.getPageSize());
        return Result.success(pageInfo);
    }

    @Override
    public Result<Object> updateGeneral(UpdateUserDTO user, HttpServletRequest request) {
        String token=request.getHeader(Constant.TOKEN);
        AuthKey authKey= (AuthKey)request.getSession().getAttribute(token);
        if(authKey.getRoleId()>Constant.ROLE_CUSTOMER_SERVICE){
            throw new CustomException(ErrorCode.EVIDENCE_UNLOCK_AUTH);
        }
        //暂时修改状态
        UserEntity userEntity=new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setStatus(user.getStatus());

        return Result.success(userDao.updateById(userEntity));
    }

    @Override
    public Result<Object> deleteGeneral(UpdateUserDTO user, HttpServletRequest request) {
        String token=request.getHeader(Constant.TOKEN);
        AuthKey authKey= (AuthKey)request.getSession().getAttribute(token);
        if(authKey.getRoleId()>Constant.ROLE_CUSTOMER_SERVICE){
            throw new CustomException(ErrorCode.EVIDENCE_UNLOCK_AUTH);
        }
        return Result.success(userDao.deleteById(user.getId()));
    }

    @Override
    public Result<Object> addSystem(SystemUserDTO user, HttpServletRequest request) {
        String token=request.getHeader(Constant.TOKEN);
        AuthKey authKey= (AuthKey)request.getSession().getAttribute(token);
        if(authKey.getRoleId()!=Constant.ROLE_SUPPER_ADMIN_USER){
            throw new CustomException(ErrorCode.EVIDENCE_UNLOCK_AUTH);
        }
        UserEntity userEntity=new UserEntity();
        userEntity.setName(user.getName());
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(Md5Util.getMD5(user.getPassword()));
        userEntity.setRole(user.getRole());
        userEntity.setStatus(1);
        return Result.success(userDao.insertOne(userEntity));
    }

    @Override
    public Result<PageInfo<SystemUserVO>> querySystem(GeneralUserDTO user, HttpServletRequest request) {
        String token=request.getHeader(Constant.TOKEN);
        AuthKey authKey= (AuthKey)request.getSession().getAttribute(token);
        if(authKey.getRoleId()>Constant.ROLE_GENERAL_ADMIN){
            throw new CustomException(ErrorCode.EVIDENCE_UNLOCK_AUTH);
        }

        IPage<UserEntity> page=userDao.querySystem(user.getStatus(),authKey.getUserId(),user.getPage(),user.getPageSize());
        List<UserEntity> list=page.getRecords();
        long total=page.getTotal();
        list=ListUtil.page(list, user.getPage(), user.getPageSize());
        List<SystemUserVO> res=new ArrayList<>(list.size());
        list.forEach(l->{
            SystemUserVO vo=new SystemUserVO();
            vo.setId(l.getId());
            vo.setUsername(l.getUsername());
            vo.setName(l.getName());
            vo.setRole(l.getRole());
            vo.setRoleName("");
            vo.setStatus(l.getId());
            res.add(vo);
        });
        PageInfo<SystemUserVO> pageInfo = new PageInfo<>();
        pageInfo.setTotal(ListUtil.isEmpty(res) ? 0 : total);
        pageInfo.setList(res);
        pageInfo.setPage(user.getPage());
        pageInfo.setPageSize(user.getPageSize());
        return Result.success(pageInfo);
    }

    @Override
    public Result<Object> updateSystem(SystemUserDTO user, HttpServletRequest request) {
        if(user.getId()==null){
            throw new CustomException(ErrorCode.SYS_PARAM_ERR);
        }
        String token=request.getHeader(Constant.TOKEN);
        AuthKey authKey= (AuthKey)request.getSession().getAttribute(token);
        if(authKey.getRoleId()!=Constant.ROLE_SUPPER_ADMIN_USER){
            throw new CustomException(ErrorCode.EVIDENCE_UNLOCK_AUTH);
        }
        UserEntity userEntity=new UserEntity();
        userEntity.setId(user.getId());
        if(user.getStatus()!=null){
            userEntity.setStatus(user.getStatus());
        }else{
            userEntity.setName(user.getName());
            userEntity.setUsername(user.getUsername());
            if(!StringUtil.isEmpty(user.getPassword())){
                userEntity.setPassword(Md5Util.getMD5(user.getPassword()));
            }
            userEntity.setRole(user.getRole());
        }
        return Result.success(userDao.updateById(userEntity));
    }

    @Override
    public Result<Object> deleteSystem(UserIdDTO user, HttpServletRequest request) {
        String token=request.getHeader(Constant.TOKEN);
        AuthKey authKey= (AuthKey)request.getSession().getAttribute(token);
        if(authKey.getRoleId()!=Constant.ROLE_SUPPER_ADMIN_USER){
            throw new CustomException(ErrorCode.EVIDENCE_UNLOCK_AUTH);
        }
        return Result.success(userDao.deleteById(user.getId()));
    }




}
