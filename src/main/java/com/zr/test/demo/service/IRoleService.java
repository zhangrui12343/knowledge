package com.zr.test.demo.service;

import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.dto.*;
import com.zr.test.demo.model.vo.GeneralUserVO;
import com.zr.test.demo.model.vo.RoleVO;
import com.zr.test.demo.model.vo.SystemUserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IRoleService {


    Result<Object> add(RoleDTO role, HttpServletRequest request);

    Result<List<RoleVO>> query(HttpServletRequest request);

    Result<Object> update(RoleDTO role, HttpServletRequest request);

    Result<Object> delete(Integer id, HttpServletRequest request);
}
