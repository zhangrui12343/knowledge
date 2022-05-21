package com.zr.test.demo.service;

import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.dto.CourseTypeDTO;
import com.zr.test.demo.model.entity.CourseTag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zr.test.demo.model.vo.CourseTypeVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zr
 * @since 2022-05-20
 */
public interface ICourseTagService extends IService<CourseTag> {

    Result<Object> add(CourseTypeDTO dto);

    Result<List<CourseTag>> findAll();

    Result<Object> update(CourseTypeDTO dto);

    Result<Object> delete(Long id);
}
