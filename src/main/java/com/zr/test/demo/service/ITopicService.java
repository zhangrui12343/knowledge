package com.zr.test.demo.service;

import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.dto.*;
import com.zr.test.demo.model.entity.Topic;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zr.test.demo.model.vo.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zr
 * @since 2022-05-24
 */
public interface ITopicService extends IService<Topic> {
    Result<Object> add(OtherCourseDTO dto, HttpServletRequest request);
    Result<PageInfo<OtherCourseVO>> query(OtherCourseQueryDTO dto, HttpServletRequest request);

    Result<OtherCourseOneVO> queryOne(Long id, HttpServletRequest request);
    Result<Object> update(OtherCourseDTO dto, HttpServletRequest request);

    Result<Object> delete(Long id, HttpServletRequest request);

    Result<Object> updateStatus(StatusDTO dto, HttpServletRequest request);

    Result<List<OtherCourseListVO>> findList();

    Result<PageInfo<OtherCourseWebVO>> listMore(OtherCourseListDTO dto);

    Result<WebAfterDetailVO> detail(Long id);
}
