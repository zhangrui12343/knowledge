package com.zr.test.demo.service;

import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.dto.AfterCourseDTO;
import com.zr.test.demo.model.dto.AfterCourseQueryDTO;
import com.zr.test.demo.model.entity.AfterCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zr.test.demo.model.vo.AfterCourseOneVO;
import com.zr.test.demo.model.vo.AfterCourseVO;
import com.zr.test.demo.model.vo.CourseVO;

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

    Result<Object> add(AfterCourseDTO dto, HttpServletRequest request);
    Result<PageInfo<AfterCourseVO>> query(AfterCourseQueryDTO dto, HttpServletRequest request);

    Result<AfterCourseOneVO> queryOne(Long id, HttpServletRequest request);
    Result<Object> update(AfterCourseDTO dto, HttpServletRequest request);

    Result<Object> delete(Long id, HttpServletRequest request);
}
