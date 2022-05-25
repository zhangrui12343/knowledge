package com.zr.test.demo.service;

import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.dto.*;
import com.zr.test.demo.model.vo.UserLoginVO;

import javax.servlet.http.HttpServletRequest;

public interface IUserService {

    Result<Object> register(UserDTO user);

    Result<UserLoginVO> login(LoginDTO user, HttpServletRequest request);

    Result<Object> logout(HttpServletRequest request);

    Result<Object> getCode(HttpServletRequest request, String phone);

    Result<Object> findPassword(HttpServletRequest request, FindPasswordDTO dto);
}
