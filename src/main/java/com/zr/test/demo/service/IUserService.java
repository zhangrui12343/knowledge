package com.zr.test.demo.service;

import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.dto.*;
import com.zr.test.demo.model.vo.GeneralUserVO;
import com.zr.test.demo.model.vo.SystemUserVO;

import javax.servlet.http.HttpServletRequest;

public interface IUserService {

    Result<Object> register(UserDTO user);

    Result<Object> login(LoginDTO user, HttpServletRequest request);


    Result<Object> logout(HttpServletRequest request);

    Result<PageInfo<GeneralUserVO>> queryGeneral(GeneralUserDTO user, HttpServletRequest request);

    Result<Object> updateGeneral(UpdateUserDTO user, HttpServletRequest key);

    Result<Object> deleteGeneral(UpdateUserDTO user, HttpServletRequest key);

    Result<Object> addSystem(SystemUserDTO user, HttpServletRequest key);

    Result<PageInfo<SystemUserVO>> querySystem(GeneralUserDTO user, HttpServletRequest key);

    Result<Object> updateSystem(SystemUserDTO user, HttpServletRequest key);

    Result<Object> deleteSystem(UserIdDTO user, HttpServletRequest key);
}
