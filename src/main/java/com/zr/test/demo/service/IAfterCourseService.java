package com.zr.test.demo.service;

import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.dto.OtherCourseDTO;
import com.zr.test.demo.model.dto.OtherCourseQueryDTO;
import com.zr.test.demo.model.dto.StatusDTO;
import com.zr.test.demo.model.entity.AfterCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zr.test.demo.model.vo.OtherCourseOneVO;
import com.zr.test.demo.model.vo.OtherCourseVO;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zr
 * @since 2022-05-20
 */
public interface IAfterCourseService extends IService<AfterCourse> {

    Result<Object> add(OtherCourseDTO dto, HttpServletRequest request);
    Result<PageInfo<OtherCourseVO>> query(OtherCourseQueryDTO dto, HttpServletRequest request);

    Result<OtherCourseOneVO> queryOne(Long id, HttpServletRequest request);
    Result<Object> update(OtherCourseDTO dto, HttpServletRequest request);

    Result<Object> delete(Long id, HttpServletRequest request);

    Result<Object> updateStatus(StatusDTO dto, HttpServletRequest request);
}
