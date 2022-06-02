package com.zr.test.demo.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author zr
 * @since 2022-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Course对象", description="")
public class CourseQueryDTO extends PageTDO implements Serializable {
    @ApiModelProperty(value = "课程名称或者授课老师", example = "1213")
    private String nameOrTeacher;

    @ApiModelProperty(value = "开始时间", example = "1213")
    private String startTime;
    @ApiModelProperty(value = "结束时间", example = "1213")
    private String endTime;
    @ApiModelProperty(value = "课程分类id 从学段到最末", example = "1213")
    private List<Long> categories;
    @ApiModelProperty(value = "课程类型id", example = "1213")
    private List<Long> types;
}
