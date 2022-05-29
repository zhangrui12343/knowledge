package com.zr.test.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zr.test.demo.model.entity.AfterCourseTagRelation;
import com.zr.test.demo.dao.AfterCourseTagRelationMapper;
import com.zr.test.demo.model.entity.Tag;
import com.zr.test.demo.service.IAfterCourseTagRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.test.demo.util.ListUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zr
 * @since 2022-05-23
 */
@Service
public class AfterCourseTagRelationServiceImpl extends ServiceImpl<AfterCourseTagRelationMapper, AfterCourseTagRelation> implements IAfterCourseTagRelationService {

}
