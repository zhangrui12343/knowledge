package com.zr.test.demo.model.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class CourseDTO implements Serializable {
    @ApiModelProperty(value = "主键id")
    private Long id;
    @NotBlank(message = "课程名称不能为空")
    @ApiModelProperty(value = "课程名称")
    private String name;
    @ApiModelProperty(value = "授课老师")
    private String teacher;
    @NotNull(message = "课程类型不能为空")
    @ApiModelProperty(value = "根节点到末级所有课程分类id")
    private List<Long> category;
    @NotNull(message = "课程类分类能为空")
    @ApiModelProperty(value = "根节点到末级所有课程类型id")
    private List<Long> courseTypeId;
    @NotNull(message = "课程标签不能为空")
    @ApiModelProperty(value = "课程标签id")
    private List<Long> courseTagIds;
    @ApiModelProperty(value = "相关应用id")
    private List<Long> apps;
    @NotBlank(message = "课程详情不能为空")
    @ApiModelProperty(value = "课程详情")
    private String description;
    @NotNull(message = "是否精品课不能为空")
    @ApiModelProperty(value = "是否精品课")
    private Integer excellent;
    @NotNull(message = "上下架不能为空")
    @ApiModelProperty(value = "上下架")
    private Integer status;
    @NotNull(message = "图片文件id能为空")
    @ApiModelProperty(value = "图片文件id")
    private Long img;
    @ApiModelProperty(value = "视频文件id")
    private Long video;
    @ApiModelProperty(value = "学习任务文件id")
    private Long learningTask;
    @ApiModelProperty(value = "课后练习id")
    private Long homework;
}
