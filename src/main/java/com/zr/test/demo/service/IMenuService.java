package com.zr.test.demo.service;

import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.dto.MenuDTO;
import com.zr.test.demo.model.dto.RoleDTO;
import com.zr.test.demo.model.vo.MenuVO;
import com.zr.test.demo.model.vo.RoleVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IMenuService {


    Result<Object> add(MenuDTO dto, HttpServletRequest request);

    Result<List<MenuVO>> query(HttpServletRequest request);

    Result<Object> update(MenuDTO dto, HttpServletRequest request);

    Result<Object> delete(Integer id, HttpServletRequest request);
}
