package com.zr.test.demo.service;

import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.dto.CourseCategoryDTO;
import com.zr.test.demo.model.dto.CourseTypeDTO;
import com.zr.test.demo.model.vo.CourseCategoryVO;
import com.zr.test.demo.model.vo.CourseTypeVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zr
 * @since 2022-05-14
 */
public interface ICourseTypeService {

    Result<Object> add(CourseTypeDTO dto);

    Result<List<CourseTypeVO>> query(HttpServletRequest request);

    Result<Object> update(CourseTypeDTO dto, HttpServletRequest request);

    Result<Object> delete(Long id, HttpServletRequest request);
}
