package com.zr.test.demo.service;

import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.dto.AppDTO;
import com.zr.test.demo.model.dto.AppQueryDTO;
import com.zr.test.demo.model.entity.App;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zr.test.demo.model.vo.AppOneVO;
import com.zr.test.demo.model.vo.AppVO;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zr
 * @since 2022-05-24
 */
public interface IAppService extends IService<App> {

    Result<Object> add(AppDTO dto, HttpServletRequest request);

    Result<PageInfo<AppVO>> queryByDto(AppQueryDTO dto, HttpServletRequest request);

    Result<AppOneVO> queryOne(Long id, HttpServletRequest request);

    Result<Object> updateByDto(AppDTO dto, HttpServletRequest request);

    Result<Object> delete(Long id, HttpServletRequest request);
}
