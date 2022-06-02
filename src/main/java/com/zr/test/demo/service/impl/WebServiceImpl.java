package com.zr.test.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.model.dto.KeywordDTO;
import com.zr.test.demo.model.entity.*;
import com.zr.test.demo.model.pojo.*;
import com.zr.test.demo.model.vo.AppAndCaseVO;
import com.zr.test.demo.model.vo.IndexVO;
import com.zr.test.demo.repository.*;
import com.zr.test.demo.service.IWebService;
import com.zr.test.demo.support.*;
import com.zr.test.demo.util.ListUtil;
import com.zr.test.demo.util.StringUtil;
import com.zr.test.demo.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class WebServiceImpl implements IWebService {

    private final CourseMapperImpl courseMapper;
    private final CourseCategoryMapperImpl categoryMapper;
    private final CourseTypeMapperImpl courseTypeMapper;
    private final CourseTagRelationMapperImpl tagRelationMapper;
    private final CarouselServiceImpl carouselService;
    private final FileRouterServiceImpl fileRouterService;
    private final UserDaoImpl userDao;
    private final FirstCategoryServiceImpl firstCategoryService;
    private final AfterCourseServiceImpl afterCourseService;
    private final AfterCourseTagRelationBiz afterCourseTagRelationBiz;
    private final AfterCourseCategoryRelationBiz afterCourseCategoryRelationBiz;
    private final TopicServiceImpl topicService;
    private final TopicTagRelationBiz topicTagRelationBiz;
    private final TopicCategoryRelationBiz topicCategoryRelationBiz;
    private final TeacherTrainingServiceImpl teacherService;
    private final TeacherTraningTagRelationBiz teacherTagRelationBiz;
    private final TeacherTraningCategoryRelationBiz teacherRelationBiz;
    private final AppServiceImpl appService;
    private final LeaderboardServiceImpl leaderboardService;
    private String tag;
    private String category;
    private String type;

    @Autowired
    public WebServiceImpl(CourseMapperImpl courseMapper, CourseCategoryMapperImpl categoryMapper,
                          CourseTypeMapperImpl courseTypeMapper, CourseTagRelationMapperImpl tagRelationMapper,
                          CarouselServiceImpl carouselService, FileRouterServiceImpl fileRouterService, UserDaoImpl userDao,
                          FirstCategoryServiceImpl firstCategoryService, AfterCourseServiceImpl afterCourseService,
                          AfterCourseTagRelationBiz afterCourseTagRelationBiz, AfterCourseCategoryRelationBiz afterCourseCategoryRelationBiz,
                          TopicServiceImpl topicService, TopicTagRelationBiz topicTagRelationBiz, TopicCategoryRelationBiz topicCategoryRelationBiz,
                          TeacherTrainingServiceImpl teacherService, TeacherTraningTagRelationBiz teacherTagRelationBiz,
                          TeacherTraningCategoryRelationBiz teacherRelationBiz, AppServiceImpl appService, LeaderboardServiceImpl leaderboardService) {
        this.courseMapper = courseMapper;
        this.categoryMapper = categoryMapper;
        this.courseTypeMapper = courseTypeMapper;
        this.tagRelationMapper = tagRelationMapper;
        this.carouselService = carouselService;
        this.fileRouterService = fileRouterService;
        this.userDao = userDao;
        this.firstCategoryService = firstCategoryService;
        this.afterCourseService = afterCourseService;
        this.afterCourseTagRelationBiz = afterCourseTagRelationBiz;
        this.afterCourseCategoryRelationBiz = afterCourseCategoryRelationBiz;
        this.topicService = topicService;
        this.topicTagRelationBiz = topicTagRelationBiz;
        this.topicCategoryRelationBiz = topicCategoryRelationBiz;
        this.teacherService = teacherService;
        this.teacherTagRelationBiz = teacherTagRelationBiz;
        this.teacherRelationBiz = teacherRelationBiz;
        this.appService = appService;
        this.leaderboardService = leaderboardService;
    }

    @Override
    public Result<Object> query(KeywordDTO dto) {
        String key = dto.getKeyword();
        tag = null;
        category = null;
        type = null;
        switch (dto.getType()) {
            case 1:
                //课程   搜课程名称 标签 大类型 小类型 老师  课程描述
                List<CourseInSearch> res = new ArrayList<>();
                QueryWrapper<CourseEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(CourseEntity::getStatus, 1).orderByDesc(CourseEntity::getTime);
                List<CourseEntity> all = courseMapper.selectList(queryWrapper);
                for (CourseEntity e : all) {
                    if (e.getName().contains(key)
                            || e.getTeacher().contains(key)
                            || e.getDescription().replace("<p>","").replace("</p>","").contains(key)
                            || existA(e.getCategory(), key)
                            || existB(e.getCourseTypeId(), key)
                            || existC(tagRelationMapper.selectTagNameByCourseId(e.getId()), key)) {
                        CourseInSearch vo = new CourseInSearch();
                        vo.setCategory(category);
                        vo.setType(type);
                        vo.setTag(tag);
                        vo.setDescription(e.getDescription());
                        vo.setId(e.getId());
                        vo.setImg(e.getImg());
                        vo.setName(e.getName());
                        vo.setTeacher(e.getTeacher());
                        res.add(vo);
                    }
                }
                int total = res.size();
                res = ListUtil.page(res, dto.getPage(), dto.getPageSize());
                PageInfo<CourseInSearch> pageInfo = new PageInfo<>();
                pageInfo.setPage(dto.getPage());
                pageInfo.setPageSize(dto.getPageSize());
                pageInfo.setList(ListUtil.isEmpty(res) ? null : res);
                pageInfo.setTotal(total);
                return Result.success(pageInfo);
            case 2:
                //课后 搜课程名称 标签 大类型 小类型  课程描述
                List<OtherInSearch> res2 = new ArrayList<>();
                QueryWrapper<AfterCourse> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.lambda().eq(AfterCourse::getStatus, 1).orderByDesc(AfterCourse::getTime);
                List<AfterCourse> all2 = afterCourseService.getBaseMapper().selectList(queryWrapper2);
                for (AfterCourse e : all2) {
                    FirstCategory firstCategory=firstCategoryService.getBaseMapper().selectById(e.getType());
                    if(firstCategory!=null){
                        type=firstCategory.getName();
                    }
                    if (e.getName().contains(key)
                            || e.getDescription().replace("<p>","").replace("</p>","").contains(key)
                            || existC(afterCourseTagRelationBiz.selectNamesByCourseId(e.getId()), key)
                            || existD(afterCourseCategoryRelationBiz.selectNamesByCourseId(e.getId()), key)
                            || existE(type,key)
                    ) {
                        OtherInSearch vo = new OtherInSearch();
                        vo.setCategory(category);
                        vo.setType(type);
                        vo.setTag(tag);
                        vo.setDescription(e.getDescription());
                        vo.setId(e.getId());
                        vo.setImg(e.getImg());
                        vo.setName(e.getName());
                        res2.add(vo);
                    }
                }
                int total2 = res2.size();
                res2 = ListUtil.page(res2, dto.getPage(), dto.getPageSize());
                PageInfo<OtherInSearch> pageInfo2 = new PageInfo<>();
                pageInfo2.setPage(dto.getPage());
                pageInfo2.setPageSize(dto.getPageSize());
                pageInfo2.setList(ListUtil.isEmpty(res2) ? null : res2);
                pageInfo2.setTotal(total2);
                return Result.success(pageInfo2);

            case 3:
                //专题 搜课程名称 标签 大类型 小类型  课程描述
                List<OtherInSearch> res3 = new ArrayList<>();
                QueryWrapper<Topic> queryWrapper3 = new QueryWrapper<>();
                queryWrapper3.lambda().eq(Topic::getStatus, 1).orderByDesc(Topic::getTime);
                List<Topic> all3 = topicService.getBaseMapper().selectList(queryWrapper3);
                for (Topic e : all3) {
                    FirstCategory firstCategory=firstCategoryService.getBaseMapper().selectById(e.getType());
                    if(firstCategory!=null){
                        type=firstCategory.getName();
                    }
                    if (e.getName().contains(key)
                            || e.getDescription().replace("<p>","").replace("</p>","").contains(key)
                            || existC(topicTagRelationBiz.selectNamesByCourseId(e.getId()), key)
                            || existD(topicCategoryRelationBiz.selectNamesByCourseId(e.getId()), key)
                            || existE(type,key)
                    ) {
                        OtherInSearch vo = new OtherInSearch();
                        vo.setCategory(category);
                        vo.setType(type);
                        vo.setTag(tag);
                        vo.setDescription(e.getDescription());
                        vo.setId(e.getId());
                        vo.setImg(e.getImg());
                        vo.setName(e.getName());
                        res3.add(vo);
                    }
                }
                int total3 = res3.size();
                res3 = ListUtil.page(res3, dto.getPage(), dto.getPageSize());
                PageInfo<OtherInSearch> pageInfo3 = new PageInfo<>();
                pageInfo3.setPage(dto.getPage());
                pageInfo3.setPageSize(dto.getPageSize());
                pageInfo3.setList(ListUtil.isEmpty(res3) ? null : res3);
                pageInfo3.setTotal(total3);
                return Result.success(pageInfo3);
            case 4:
                //师研 搜课程名称 标签 大类型 小类型  课程描述
                List<OtherInSearch> res4 = new ArrayList<>();
                QueryWrapper<TeacherTraining> queryWrapper4 = new QueryWrapper<>();
                queryWrapper4.lambda().eq(TeacherTraining::getStatus, 1).orderByDesc(TeacherTraining::getTime);
                List<TeacherTraining> all4 = teacherService.getBaseMapper().selectList(queryWrapper4);
                for (TeacherTraining e : all4) {
                    FirstCategory firstCategory=firstCategoryService.getBaseMapper().selectById(e.getType());
                    if(firstCategory!=null){
                        type=firstCategory.getName();
                    }
                    if (e.getName().contains(key)
                            || e.getDescription().replace("<p>","").replace("</p>","").contains(key)
                            || existC(teacherTagRelationBiz.selectNamesByCourseId(e.getId()), key)
                            || existD(teacherRelationBiz.selectNamesByCourseId(e.getId()), key)
                            ||existE(type,key)
                    ) {
                        OtherInSearch vo = new OtherInSearch();
                        vo.setCategory(category);
                        vo.setType(type);
                        vo.setTag(tag);
                        vo.setDescription(e.getDescription());
                        vo.setId(e.getId());
                        vo.setImg(e.getImg());
                        vo.setName(e.getName());
                        res4.add(vo);
                    }
                }
                int total4 = res4.size();
                res4 = ListUtil.page(res4, dto.getPage(), dto.getPageSize());
                PageInfo<OtherInSearch> pageInfo4 = new PageInfo<>();
                pageInfo4.setPage(dto.getPage());
                pageInfo4.setPageSize(dto.getPageSize());
                pageInfo4.setList(ListUtil.isEmpty(res4) ? null : res4);
                pageInfo4.setTotal(total4);
                return Result.success(pageInfo4);
            default:
                throw new CustomException(ErrorCode.SYS_PARAM_ERR);
        }

    }

    private boolean existA(String string, String key) {
        List<Long> ids = ListUtil.stringToList(string);
        if (!ListUtil.isEmpty(ids)) {
            List<CourseCategoryEntity> entities = categoryMapper.selectByIds(ids);
            if (!ListUtil.isEmpty(entities)) {
                for (CourseCategoryEntity e : entities) {
                    if (e.getName().contains(key)) {
                        category = e.getName();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean existB(String string, String key) {
        List<Long> ids = ListUtil.stringToList(string);
        if (!ListUtil.isEmpty(ids)) {
            List<CourseTypeEntity> entities = courseTypeMapper.selectByIds(ids);
            if (!ListUtil.isEmpty(entities)) {
                for (CourseTypeEntity e : entities) {
                    if (e.getName().contains(key)) {
                        type = e.getName();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean existC(List<String> names, String key) {
        if (!ListUtil.isEmpty(names)) {
            for (String e : names) {
                if (e.contains(key)) {
                    tag = e;
                    return true;
                }
            }
        }
        return false;
    }
    private boolean existE(String name, String key) {
        if (!StringUtil.isEmpty(name)) {
           return name.contains(key);
        }
        return false;
    }

    private boolean existD(List<String> names, String key) {
        if (!ListUtil.isEmpty(names)) {
            for (String e : names) {
                if (e.contains(key)) {
                    category = e;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Result<IndexVO> index() {
        IndexVO vo = new IndexVO();
        //轮播图
        vo.setImages(getImage());
        //统计
        vo.setCount(getCount());
        QueryWrapper<FirstCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("`order`");
        List<FirstCategory> firstCategories = firstCategoryService.getBaseMapper().selectList(queryWrapper);
        List<FirstCategory> after = new ArrayList<>();
        List<FirstCategory> topic = new ArrayList<>();
        List<FirstCategory> teacher = new ArrayList<>();
        firstCategories.forEach(e -> {
            if (e.getType() == 0) {
                after.add(e);
            } else if (e.getType() == 1) {
                topic.add(e);
            } else {
                teacher.add(e);
            }
        });

        //课后服务
        vo.setAfterCourses(getAfter(after));
        //专题
        vo.setTopics(getTopic(topic));
        //师研
        vo.setTeacherTrainings(getTearch(teacher));
        //app
        vo.setApps(getApps());
        //排行榜
        vo.setLeaderboard(getLeader());

        return Result.success(vo);

    }

    private LeaderInIndex getLeader() {
        LeaderInIndex vo = new LeaderInIndex();
        QueryWrapper<Leaderboard> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByDesc(Leaderboard::getOrderr);
        List<Leaderboard> list = leaderboardService.getBaseMapper().selectList(queryWrapper);
        List<Leaderboard> list0 = new ArrayList<>();
        List<Leaderboard> list1 = new ArrayList<>();
        List<Leaderboard> list2 = new ArrayList<>();
        if (ListUtil.isEmpty(list)) {
            vo.setSchool(list0);
            vo.setSubject(list1);
            vo.setTeacher(list2);
            return vo;
        }
        list.forEach(e -> {
            if (e.getType() == 0) {
                list0.add(e);
            } else if (e.getType() == 1) {
                list1.add(e);
            } else {
                list2.add(e);
            }
        });
        vo.setSubject(list0);
        vo.setSchool(list1);
        vo.setTeacher(list2);
        return vo;
    }

    private List<AppInIndex> getApps() {
        List<AppInIndex> res = new ArrayList<>();
        List<AppAndCaseVO> caseApps = this.appService.getBaseMapper().selectByType(null);
        if (ListUtil.isEmpty(caseApps)) {
            return res;
        }
        LinkedHashMap<String, AppAndCaseVO> map = new LinkedHashMap<>(10);
        caseApps.forEach(caseApp -> {
            String key = caseApp.getId().toString();
            if (map.containsKey(key)) {
                if (map.get(key).getCaseorder() < caseApp.getCaseorder()) {
                    map.put(key, caseApp);
                }
            } else {
                map.put(key, caseApp);
            }
        });
        List<AppAndCaseVO> list = new ArrayList<>(map.values());
        for (AppAndCaseVO vo : list) {
            AppInIndex index = new AppInIndex();
            index.setId(vo.getCaseid());
            index.setLogo(vo.getLogo());
            res.add(index);
            if (res.size() == 12) {
                break;
            }
        }
        return res;
    }

    private List<TeacherInIndex> getTearch(List<FirstCategory> teacher) {
        List<TeacherInIndex> res = new ArrayList<>(4);
        for (FirstCategory firstCategory : teacher) {
            TeacherInIndex index = new TeacherInIndex();
            index.setId(firstCategory.getId());
            index.setName(firstCategory.getName());
            index.setImg(firstCategory.getImg());
            res.add(index);
            if (res.size() == 4) {
                break;
            }
        }
        return res;
    }

    private List<TopicInIndex> getTopic(List<FirstCategory> firstCategories) {
        List<TopicInIndex> res = new ArrayList<>(6);
        for (FirstCategory firstCategory : firstCategories) {
            TopicInIndex index = new TopicInIndex();
            index.setId(firstCategory.getId());
            index.setName(firstCategory.getName());
            index.setImg(firstCategory.getImg());
            List<String> t = firstCategoryService.getBaseMapper().selectCategoryNamesByFirstId(firstCategory.getId());
            index.setCategories(t == null ? new ArrayList<>() : t);
            res.add(index);
            if (res.size() == 6) {
                break;
            }
        }
        return res;
    }

    private List<AfterCourseInIndex> getAfter(List<FirstCategory> firstCategories) {
        List<AfterCourseInIndex> res = new ArrayList<>();
        firstCategories.forEach(e -> {
            AfterCourseInIndex index = new AfterCourseInIndex();
            index.setId(e.getId());
            index.setName(e.getName());
            QueryWrapper<AfterCourse> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(AfterCourse::getType, e.getId()).eq(AfterCourse::getStatus, 1).orderByDesc(AfterCourse::getTime).last("limit 10");
            List<AfterCourse> afterCourses = afterCourseService.getBaseMapper().selectList(queryWrapper);
            if (ListUtil.isEmpty(afterCourses)) {
                index.setAfterCourseOne(new ArrayList<>());
            } else {
                List<AfterCourseInIndex.AfterCourseOne> afterCourseOneList = new ArrayList<>();
                afterCourses.forEach(afterCourse -> {
                    AfterCourseInIndex.AfterCourseOne afterCourseOne = new AfterCourseInIndex.AfterCourseOne();
                    afterCourseOne.setCount(afterCourse.getCount());
                    afterCourseOne.setDate(TimeUtil.getTime(afterCourse.getTime()));
                    afterCourseOne.setName(afterCourse.getName());
                    afterCourseOne.setImg(afterCourse.getImg());
                    afterCourseOne.setCategories(afterCourseCategoryRelationBiz.selectNamesByCourseId(afterCourse.getId()));
                    afterCourseOne.setTags(afterCourseTagRelationBiz.selectNamesByCourseId(afterCourse.getId()));
                    afterCourseOneList.add(afterCourseOne);
                });
                index.setAfterCourseOne(afterCourseOneList);
            }
            res.add(index);
        });
        return res;
    }

    private CountInIndex getCount() {
        CountInIndex countInIndex = new CountInIndex();
        //今日更新资源数
        QueryWrapper<FileRouter> queryWrapper = new QueryWrapper<>();
        Date now = new Date();
        queryWrapper.lambda().between(FileRouter::getCreateTime, TimeUtil.getDate(TimeUtil.getDayStart(now)), now);
        List<FileRouter> list = fileRouterService.getBaseMapper().selectList(queryWrapper);
        if (ListUtil.isEmpty(list)) {
            countInIndex.setToday(0);
            countInIndex.setFileAdd(0.0);
        }
        countInIndex.setToday(list.size());
        BigDecimal bigDecimal = BigDecimal.valueOf(list.stream().filter(Objects::nonNull).mapToLong(FileRouter::getSize).sum());
        bigDecimal = bigDecimal.divide(new BigDecimal("1024"), 4, BigDecimal.ROUND_HALF_UP)
                .divide(new BigDecimal("1024"), 2, BigDecimal.ROUND_HALF_UP);
        countInIndex.setFileAdd(bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());

        //昨日资源数
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date yesterday = cal.getTime();
        queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().between(FileRouter::getCreateTime,
                TimeUtil.getDate(TimeUtil.getDayStart(yesterday)), TimeUtil.getDate(TimeUtil.getDayEnd(yesterday)));
        countInIndex.setYesterday(fileRouterService.getBaseMapper().selectCount(queryWrapper));

        countInIndex.setVips(userDao.selectCount());

        countInIndex.setFileTotal(fileRouterService.getBaseMapper().selectCount(new QueryWrapper<>()));

        countInIndex.setSchools((int) userDao.selectSchoolCount());

        return countInIndex;
    }

    private List<CarouselInIndex> getImage() {
        QueryWrapper<Carousel> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Carousel::getStatus, 1).orderByDesc(Carousel::getOrderr);
        List<Carousel> carousels = carouselService.getBaseMapper().selectList(queryWrapper);
        if (ListUtil.isEmpty(carousels)) {
            return new ArrayList<>();
        }
        List<CarouselInIndex> res = new ArrayList<>();
        carousels.forEach(carousel -> {
            CarouselInIndex index = new CarouselInIndex();
            index.setImg(carousel.getImg());
            index.setUrl(carousel.getUrl());
            res.add(index);
        });
        return res;
    }


}
