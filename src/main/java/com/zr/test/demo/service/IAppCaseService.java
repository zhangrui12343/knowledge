package com.zr.test.demo.service;

import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.dto.AppCaseDTO;
import com.zr.test.demo.model.dto.AppQueryDTO;
import com.zr.test.demo.model.dto.StatusDTO;
import com.zr.test.demo.model.entity.AppCase;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zr.test.demo.model.vo.AppCaseDetailVO;
import com.zr.test.demo.model.vo.AppCaseOneVO;
import com.zr.test.demo.model.vo.AppCaseVO;

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

    Result<PageInfo<AppCaseVO>> queryByDto(AppQueryDTO dto, HttpServletRequest request);

    Result<AppCaseOneVO> queryOne(Long id, HttpServletRequest request);

    Result<Object> updateByDto(AppCaseDTO dto, HttpServletRequest request);

    Result<Object> delete(Long id, HttpServletRequest request);

    Result<Object> updateStatus(StatusDTO dto, HttpServletRequest request);

    Result<AppCaseDetailVO> detail(Long id);
}
