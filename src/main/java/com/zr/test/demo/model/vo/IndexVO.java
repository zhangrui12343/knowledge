package com.zr.test.demo.model.vo;

import com.zr.test.demo.model.pojo.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class IndexVO {
    @ApiModelProperty(value = "轮播图")
    private List<CarouselInIndex> images;

    @ApiModelProperty(value = "统计")
    private CountInIndex count;

    @ApiModelProperty(value = "课后服务")
    private List<AfterCourseInIndex> afterCourses;

    @ApiModelProperty(value = "专题")
    private List<TopicInIndex> topics;

    @ApiModelProperty(value = "师研")
    private List<TeacherInIndex> teacherTrainings;

    @ApiModelProperty(value = "信课融合")
    private List<AppInIndex> apps;

    @ApiModelProperty(value = "排行榜")
    private LeaderInIndex leaderboard;


}
