package com.zr.test.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zr.test.demo.common.Constant;
import com.zr.test.demo.common.Request;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.dao.SecondCategoryMapper;
import com.zr.test.demo.dao.TagMapper;
import com.zr.test.demo.model.dto.FirstCategoryDTO;
import com.zr.test.demo.model.entity.FirstCategory;
import com.zr.test.demo.dao.FirstCategoryMapper;
import com.zr.test.demo.model.entity.SecondCategory;
import com.zr.test.demo.model.entity.Tag;
import com.zr.test.demo.model.vo.FirstCategoryVO;
import com.zr.test.demo.repository.FileRouterMapperImpl;
import com.zr.test.demo.service.IFirstCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.test.demo.util.FileUtil;
import com.zr.test.demo.util.ListUtil;
import com.zr.test.demo.util.StringUtil;
import jdk.nashorn.internal.runtime.options.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zr
 * @since 2022-05-16
 */
@Service
@Slf4j
public class FirstCategoryServiceImpl extends ServiceImpl<FirstCategoryMapper, FirstCategory> implements IFirstCategoryService {
    private final FileRouterMapperImpl fileRouterMapper;
    private final TagMapper tagMapper;
    private final SecondCategoryMapper secondCategoryMapper;

    public FirstCategoryServiceImpl(FileRouterMapperImpl fileRouterMapper, TagMapper tagMapper, SecondCategoryMapper secondCategoryMapper) {
        this.fileRouterMapper = fileRouterMapper;
        this.tagMapper = tagMapper;
        this.secondCategoryMapper = secondCategoryMapper;
    }

    @Override
    public Result<Object> add(FirstCategoryDTO dto) {
        FirstCategory entity = new FirstCategory();
        BeanUtils.copyProperties(entity, dto);
        entity.setType(Constant.AFTER_COURSE);
        return Result.success(this.getBaseMapper().insert(entity));
    }


    @Override
    public Result<List<FirstCategoryVO>> queryByType(Integer type) {
        QueryWrapper<FirstCategory> queryWrapper = new QueryWrapper<>(null);
        queryWrapper.eq("type", type);
        queryWrapper.orderByDesc("order");
        List<FirstCategory> list = this.getBaseMapper().selectList(queryWrapper);
        List<FirstCategoryVO> res = new ArrayList<>();
        List<Tag> tags = tagMapper.selectList(null);
        Map<Long, String> tagMap = tags.stream().collect(Collectors.toMap(Tag::getId, Tag::getName));
        List<SecondCategory> categories = secondCategoryMapper.selectList(null);
        Map<Long, String> cMap = categories.stream().collect(Collectors.toMap(SecondCategory::getId, SecondCategory::getName));
        list.forEach(e -> {
            FirstCategoryVO vo = new FirstCategoryVO();
            BeanUtils.copyProperties(e, vo);
            vo.setImg(FileUtil.getBase64FilePath(fileRouterMapper.getPathById(e.getImg())));
            vo.setCategory(Optional.ofNullable(cMap.get(e.getTag())).orElse(""));
            vo.setTag(Optional.ofNullable(tagMap.get(e.getTag())).orElse(""));
            res.add(vo);
        });
        return Result.success(res);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> delete(Long id) {
        FirstCategory e = this.getBaseMapper().selectById(id);
        if (e == null) {
            return Result.success(null);
        }
        String path = fileRouterMapper.getPathById(e.getImg());
        if (!StringUtil.isEmpty(path)) {
            File f = new File(path);
            if (f.exists()) {
                if (f.delete()) {
                    fileRouterMapper.deleteById(e.getImg());
                }else {
                    log.error("文件删除失败，path={},id={}",path, e.getImg());
                }
            }
        }
        return Result.success(this.getBaseMapper().deleteById(id));
    }

    @Override
    public Result<Object> update(FirstCategoryDTO dto) {
        if (dto.getId() == null) {
            throw new CustomException(ErrorCode.SYS_PARAM_INNER_ERR);
        }
        FirstCategory entity = new FirstCategory();
        BeanUtils.copyProperties(entity, dto);
        return Result.success(this.getBaseMapper().updateById(entity));
    }
}
