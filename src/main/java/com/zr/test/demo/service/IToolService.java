package com.zr.test.demo.service;

import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.entity.Tool;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zr.test.demo.model.entity.ToolAppRelation;
import com.zr.test.demo.model.vo.ToolVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zr
 * @since 2022-05-25
 */
public interface IToolService extends IService<Tool> {

    Result<Object> add(Tool dto, HttpServletRequest request);

    Result<List<ToolVO>> queryAll();

    Result<Object> addRelation(ToolAppRelation dto, HttpServletRequest request);

    Result<Object> delete(Long id, HttpServletRequest request);

    Result<Object> deleteRelation(ToolAppRelation id, HttpServletRequest request);

    Result<Object> updateByDto(Tool dto, HttpServletRequest request);
}
