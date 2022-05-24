package com.zr.test.demo.service.impl;

import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.dto.AppCaseDTO;
import com.zr.test.demo.model.entity.AppCase;
import com.zr.test.demo.dao.AppCaseMapper;
import com.zr.test.demo.service.IAppCaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zr
 * @since 2022-05-24
 */
@Service
public class AppCaseServiceImpl extends ServiceImpl<AppCaseMapper, AppCase> implements IAppCaseService {

    @Override
    public Result<Object> add(AppCaseDTO dto, HttpServletRequest request) {
        AppCase appCase=new AppCase();
        BeanUtils.copyProperties(dto,appCase);
        return Result.success(this.getBaseMapper().insert(appCase));
    }
}
