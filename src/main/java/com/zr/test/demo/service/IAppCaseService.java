package com.zr.test.demo.service;

import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.dto.AppCaseDTO;
import com.zr.test.demo.model.entity.AppCase;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zr
 * @since 2022-05-24
 */
public interface IAppCaseService extends IService<AppCase> {

    Result<Object> add(AppCaseDTO dto, HttpServletRequest request);
}
