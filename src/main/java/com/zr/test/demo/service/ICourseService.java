package com.zr.test.demo.service;


import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.dto.CourseDTO;
import com.zr.test.demo.model.dto.CourseQueryDTO;
import com.zr.test.demo.model.vo.CourseOneVO;
import com.zr.test.demo.model.vo.CourseVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zr
 * @since 2022-05-14
 */
public interface ICourseService {

    Result<Object> add(CourseDTO dto, MultipartFile img, MultipartFile learningTask, MultipartFile homework, MultipartFile video, HttpServletRequest request);

    Result<PageInfo<CourseVO>> query(CourseQueryDTO dto, HttpServletRequest request);

    Result<Object> update(CourseDTO dto, MultipartFile img, MultipartFile[] pdf, MultipartFile video, HttpServletRequest request);

    Result<Object> delete(Long id, HttpServletRequest request);

    Result<CourseOneVO> queryOne(Long id, HttpServletRequest request);
}
