package com.zr.test.demo.service;

import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.dto.FirstCategoryDTO;
import com.zr.test.demo.model.entity.FirstCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zr.test.demo.model.vo.FirstCategoryOneVO;
import com.zr.test.demo.model.vo.FirstCategoryVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zr
 * @since 2022-05-16
 */
public interface IFirstCategoryService extends IService<FirstCategory> {

    Result<Object> add(FirstCategoryDTO dto);

    Result<List<FirstCategoryVO>> queryByType(Integer type);

    Result<Object> delete(Long id);

    Result<Object> update(FirstCategoryDTO dto);

    Result<FirstCategoryOneVO> queryOne(Long id);
}
