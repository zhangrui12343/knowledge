package com.zr.test.demo.service;

import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.dto.*;
import com.zr.test.demo.model.vo.GeneralUserVO;
import com.zr.test.demo.model.vo.StudentVO;
import com.zr.test.demo.model.vo.SysLoginVO;
import com.zr.test.demo.model.vo.SystemUserVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface ISysUserService {


    Result<SysLoginVO> login(SysLoginDTO user, HttpServletRequest request);

    Result<PageInfo<GeneralUserVO>> queryGeneral(GeneralUserDTO user, HttpServletRequest request);

    Result<Object> deleteGeneral(UpdateStudentDTO user, HttpServletRequest key);

    Result<Object> addSystem(SystemUserDTO user, HttpServletRequest key);

    Result<PageInfo<SystemUserVO>> querySystem(GeneralUserDTO status, HttpServletRequest key);

    Result<Object> updateSystem(SystemUserDTO user, HttpServletRequest key);

    Result<Object> deleteSystem(UserIdDTO user, HttpServletRequest key);

    Result<Object> updateSystemPassword(SystemPasswordDTO user, HttpServletRequest request);

    Result<PageInfo<StudentVO>> queryStudent(StudentDTO user, HttpServletRequest request);

    Result<Object> updateStudent(UpdateStudentDTO user, HttpServletRequest request);

    Result<Object> importStudent(MultipartFile file, HttpServletRequest request);
}
