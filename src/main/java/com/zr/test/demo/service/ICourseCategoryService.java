package com.zr.test.demo.service;

import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.dto.CourseCategoryDTO;
import com.zr.test.demo.model.entity.CourseCategoryEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zr.test.demo.model.vo.CourseCategoryVO;

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
public interface ICourseCategoryService {

    Result<Object> add(CourseCategoryDTO dto);

    Result<List<CourseCategoryVO>> query(HttpServletRequest request);

    Result<Object> update(CourseCategoryDTO dto, HttpServletRequest request);

    Result<Object> delete(Long id, HttpServletRequest request);
}
