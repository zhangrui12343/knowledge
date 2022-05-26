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
import com.zr.test.demo.model.vo.UserLoginVO;
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
import java.util.Optional;

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
        UserEntity userEntity = new UserEntity();
        if (user.getStudent() == 1) {
            //学生用户
            if (StringUtil.isEmpty(user.getSchool())) {
                throw new CustomException(ErrorCode.SYS_PARAM_ERR, "学校名称不能为空!");
            }
            if (StringUtil.isEmpty(user.getStudentNo())) {
                throw new CustomException(ErrorCode.SYS_PARAM_ERR, "学籍号不能为空!");
            }
            //判断学籍号是否存在
            userEntity.setStudent(1);
            userEntity.setStudentNo(user.getStudentNo());
            List<UserEntity> list = userDao.selectByEntity(userEntity);
            if (!ListUtil.isEmpty(list)) {
                return Result.fail(ErrorCode.SYS_USERNAME_EXIST_ERROR_ERR, "学籍号已存在");
            }
            userEntity.setSchool(user.getSchool());
            userEntity.setIntranet(0);
        } else if (user.getStudent() == 0) {
            //普通用户
            if (StringUtil.isEmpty(user.getPhone())) {
                throw new CustomException(ErrorCode.SYS_PARAM_ERR, "手机号不能为空!");
            }
            if (StringUtil.isEmpty(user.getPassword())) {
                throw new CustomException(ErrorCode.SYS_PARAM_ERR, "密码不能为空!");
            }
            //判断手机号是否存在
            userEntity.setStudent(0);
            userEntity.setPhone(user.getPhone());
            List<UserEntity> list = userDao.selectByEntity(userEntity);
            if (!ListUtil.isEmpty(list)) {
                return Result.fail(ErrorCode.SYS_USERNAME_EXIST_ERROR_ERR, "手机号已存在");
            }
            userEntity.setPhone(user.getPhone());
            userEntity.setPassword(Md5Util.getMD5(user.getPassword()));
        } else {
            throw new CustomException(ErrorCode.SYS_PARAM_ERR);
        }
        userEntity.setName(user.getName());
        userEntity.setStatus(1);
        userEntity.setRegister(TimeUtil.getTime());
        userDao.insertOne(userEntity);
        return Result.success("注册成功");
    }

    @Override
    public Result<UserLoginVO> login(LoginDTO loginDTO, HttpServletRequest request) {
        UserEntity userEntity = new UserEntity();
        UserEntity user;
        UserLoginVO vo=new UserLoginVO();
        if (loginDTO.getStudent() == 1) {
            //校验参数
            if (StringUtil.isEmpty(loginDTO.getName())) {
                throw new CustomException(ErrorCode.SYS_PARAM_ERR, "姓名不能为空");
            }
            if (StringUtil.isEmpty(loginDTO.getStudentNo())) {
                throw new CustomException(ErrorCode.SYS_PARAM_ERR, "学籍号不能为空");
            }
            //判断学籍号是否存在
            userEntity.setStudentNo(loginDTO.getStudentNo());
            List<UserEntity> list = userDao.selectByEntity(userEntity);
            if (ListUtil.isEmpty(list)) {
                //学籍号不存在
                return Result.fail(ErrorCode.SYS_USERNAME_EXIST_ERROR_ERR, "学籍号不存在",vo);
            }
            if (list.size() > 1) {
                throw new CustomException(ErrorCode.SEARCH_TERREC_FAIL, "查询错误，请联系管理员");
            }
            user = list.get(0);
            if (!user.getName().equals(loginDTO.getName())) {
                return Result.fail(ErrorCode.SYS_USER_OR_PWD_ERROR_ERR, "姓名错误",vo);
            }
        } else if (loginDTO.getStudent() == 0) {
            //校验参数
            if (StringUtil.isEmpty(loginDTO.getPhone())) {
                throw new CustomException(ErrorCode.SYS_PARAM_ERR, "手机号不能为空");
            }
            if (StringUtil.isEmpty(loginDTO.getPassword())) {
                throw new CustomException(ErrorCode.SYS_PARAM_ERR, "密码不能为空");
            }
            //判断手机号是否存在
            userEntity.setPhone(loginDTO.getPhone());
            List<UserEntity> list = userDao.selectByEntity(userEntity);
            if (ListUtil.isEmpty(list)) {
                //手机号不存在
                return Result.fail(ErrorCode.SYS_USERNAME_EXIST_ERROR_ERR, "手机号不存在",vo);
            }
            if (list.size() > 1) {
                throw new CustomException(ErrorCode.SEARCH_TERREC_FAIL, "查询错误，请联系管理员");
            }
            user = list.get(0);
            if (!user.getPassword().equals(Md5Util.getMD5(loginDTO.getPassword()))) {
                return Result.fail(ErrorCode.SYS_USER_OR_PWD_ERROR_ERR,vo);
            }
        } else {
            throw new CustomException(ErrorCode.SYS_PARAM_ERR);
        }
        if (user.getStatus() == 0) {
            return Result.fail(ErrorCode.SYS_ACCOUNT_HAS_BANED_ERR,vo);
        }
        //登录成功后的操作
        user.setLastLogin(TimeUtil.getTime());
        userDao.updateById(user);
        String token;
        try {
            //是否是系统用户|userid|time|是否是学生|是否是内网|权限id
            token = DESUtils.encrypt(DESUtils.KEY_DEFALUT, MessageFormat.format("{0}|{1}|{2}|{3}|{4}|{5}",
                    0, user.getId(), TimeUtil.getTime(), user.getStudent(), Optional.ofNullable(user.getIntranet()).orElse(0),-1));
        } catch (Exception e) {
            log.error("make token失败{}",e.getMessage(),e);
            throw new CustomException(ErrorCode.SYS_ENCRIPT_ERR, e.getMessage());
        }
        if (token == null) {
            throw new CustomException(ErrorCode.SYS_PARAM_INNER_ERR, "des encrypt key = null");
        }
        try {
            token = URLEncoder.encode(token, "utf-8");
        } catch (Exception e) {
            log.error("key des urlencode error ：" + e.getMessage(), e);
            throw new CustomException(ErrorCode.SYS_PARAM_INNER_ERR, "key urlencode error");
        }
        AuthKey authKey = new AuthKey();
        authKey.setIntranet(0);
        authKey.setSystem(0);
        authKey.setStudent(user.getStudent());
        authKey.setRoleId(-1);
        authKey.setUserId(user.getId());
        authKey.setTime(TimeUtil.getTime());
        request.getSession().setAttribute(token, authKey);
        vo.setToken(token);
        vo.setUsername(user.getName());
        return Result.success(vo);

    }


    @Override
    public Result<Object> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return Result.success(null);
    }

    @Override
    public Result<Object> getCode(HttpServletRequest request, String phone) {
        //通过手机号发送到阿里云短袖服务 获得验证码
        return null;
    }

    @Override
    public Result<Object> findPassword(HttpServletRequest request, FindPasswordDTO dto) {
        if(!"123456".equals(dto.getCode())){
            return Result.fail(ErrorCode.SYS_CODE_ERRO);
        }
        UserEntity userEntity=new UserEntity();
        //判断手机号是否存在
        userEntity.setPhone(dto.getPhone());
        List<UserEntity> list = userDao.selectByEntity(userEntity);
        if (ListUtil.isEmpty(list)) {
            //手机号不存在
            return Result.fail(ErrorCode.SYS_USERNAME_EXIST_ERROR_ERR, "手机号不存在");
        }
        if (list.size() > 1) {
            throw new CustomException(ErrorCode.SEARCH_TERREC_FAIL, "查询错误，请联系管理员");
        }
        UserEntity user = list.get(0);
        user.setPassword(Md5Util.getMD5(dto.getPassword()));
        return Result.success(userDao.updateById(user));
    }
}
