package com.zr.test.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zr.test.demo.common.Constant;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.dao.SecondCategoryMapper;
import com.zr.test.demo.dao.TagMapper;
import com.zr.test.demo.model.dto.FirstCategoryDTO;
import com.zr.test.demo.model.entity.*;
import com.zr.test.demo.dao.FirstCategoryMapper;
import com.zr.test.demo.model.vo.FirstCategoryOneVO;
import com.zr.test.demo.model.vo.FirstCategoryVO;
import com.zr.test.demo.repository.FileRouterMapperImpl;
import com.zr.test.demo.service.IFirstCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.test.demo.util.FileUtil;
import com.zr.test.demo.util.ListUtil;
import com.zr.test.demo.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;
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

    private final FirstTagServiceImpl firstTagService;
    private final SecondCategoryMapper secondCategoryMapper;

    public FirstCategoryServiceImpl(FileRouterMapperImpl fileRouterMapper, TagMapper tagMapper,
                                  FirstTagServiceImpl firstTagService, SecondCategoryMapper secondCategoryMapper) {
        this.fileRouterMapper = fileRouterMapper;
        this.tagMapper = tagMapper;

        this.firstTagService = firstTagService;
        this.secondCategoryMapper = secondCategoryMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> add(FirstCategoryDTO dto) {
        FirstCategory entity = new FirstCategory();
        BeanUtils.copyProperties(dto, entity);
        int i = this.getBaseMapper().insert(entity);
        if (i == 1) {
            dto.getTag().forEach(id -> firstTagService.getBaseMapper().insert(new FirstTag(entity.getId(), id)));
        }
        return Result.success(i);
    }


    @Override
    public Result<List<FirstCategoryVO>> queryByType(Integer type) {
        QueryWrapper<FirstCategory> queryWrapper = new QueryWrapper<>(null);
        queryWrapper.eq("type", type);
        queryWrapper.orderByDesc("`order`");
        List<FirstCategory> list = this.getBaseMapper().selectList(queryWrapper);
        List<FirstCategoryVO> res = new ArrayList<>();
        List<Tag> tags = tagMapper.selectList(null);
        Map<Long, String> tagMap = tags.stream().collect(Collectors.toMap(Tag::getId, Tag::getName));
        List<SecondCategory> categories = secondCategoryMapper.selectList(null);
        Map<Long, String> cMap = categories.stream().collect(Collectors.toMap(SecondCategory::getId, SecondCategory::getName));


        Map<Long, StringBuilder> tagRelationMap = new HashMap<>();
        firstTagService.getBaseMapper().selectList(null).forEach(r -> {
            String name = tagMap.get(r.getTagId());
            if (StringUtil.isEmpty(name)) {
                return;
            }
            if (tagRelationMap.containsKey(r.getFirstId())) {
                tagRelationMap.get(r.getFirstId()).append(name).append(",");
            } else {
                tagRelationMap.put(r.getFirstId(), new StringBuilder(name + ","));
            }
        });
        list.forEach(e -> {
            FirstCategoryVO vo = new FirstCategoryVO();
            BeanUtils.copyProperties(e, vo);
            vo.setImg(FileUtil.getBase64FilePath(fileRouterMapper.getPathById(e.getImg())));
            vo.setImgId(e.getImg());

            vo.setCategory(Optional.ofNullable(cMap.get(e.getCategory())).orElse(""));
            vo.setCategoryId(e.getCategory());
            StringBuilder sb1 = tagRelationMap.get(e.getId());
            if (sb1 != null) {
                vo.setTag(sb1.substring(0, sb1.length() - 1));
            }
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
        firstTagService.getBaseMapper().delete(new QueryWrapper<>(new FirstTag(id, null)));
        String path = fileRouterMapper.getPathById(e.getImg());
        if (!StringUtil.isEmpty(path)) {
            File f = new File(path);
            if (f.exists()) {
                if (f.delete()) {
                    fileRouterMapper.deleteById(e.getImg());
                } else {
                    log.error("文件删除失败，path={},id={}", path, e.getImg());
                }
            }
        }
        return Result.success(this.getBaseMapper().deleteById(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> update(FirstCategoryDTO dto) {
        if (dto.getId() == null) {
            throw new CustomException(ErrorCode.SYS_PARAM_INNER_ERR);
        }
        FirstCategory old = this.getBaseMapper().selectById(dto.getId());
        if (!old.getImg().equals(dto.getImg())) {
            fileRouterMapper.deleteById(old.getImg());
        }
        FirstCategory entity = new FirstCategory();
        BeanUtils.copyProperties(entity, dto);
        firstTagService.getBaseMapper().delete(new QueryWrapper<>(new FirstTag(dto.getId(), null)));
        dto.getTag().forEach(id -> firstTagService.getBaseMapper().insert(new FirstTag(dto.getId(), id)));
        return Result.success(this.getBaseMapper().updateById(entity));
    }

    @Override
    public Result<FirstCategoryOneVO> queryOne(Long id) {
        FirstCategory firstCategory=this.baseMapper.selectById(id);
        if(firstCategory==null){
            return Result.success(null);
        }
        FirstCategoryOneVO vo=new FirstCategoryOneVO();
        BeanUtils.copyProperties(firstCategory,vo);
        vo.setImg(fileRouterMapper.getPathById(firstCategory.getImg()));
        vo.setImgId(firstCategory.getImg());
        FirstTag tag=new FirstTag();
        tag.setFirstId(id);
        List<FirstTag> firstTags= firstTagService.getBaseMapper().selectList(new QueryWrapper<>(tag));
        if(!ListUtil.isEmpty(firstTags)){
            vo.setTag(firstTags.stream().map(FirstTag::getTagId).collect(Collectors.toList()));
        }
        return Result.success(vo);
    }
}
