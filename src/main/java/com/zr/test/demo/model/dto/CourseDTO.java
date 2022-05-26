package com.zr.test.demo.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
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
    @NotBlank
    @ApiModelProperty(value = "课程名称")
    private String name;
    @ApiModelProperty(value = "授课老师")
    private String teacher;
    @NotNull
    @ApiModelProperty(value = "课程类型id")
    private Long category;
    @NotNull
    @ApiModelProperty(value = "课程分类id")
    private Long courseTypeId;
    @NotNull
    @ApiModelProperty(value = "课程标签id")
    private List<Long> courseTagIds;
    @ApiModelProperty(value = "相关应用id")
    private List<Long> apps;
    @NotBlank
    @ApiModelProperty(value = "课程详情")
    private String description;
    @NotNull
    @ApiModelProperty(value = "是否精品课")
    private Integer excellent;
    @NotNull
    @ApiModelProperty(value = "上下架")
    private Integer status;
    @NotNull
    @ApiModelProperty(value = "图片文件id")
    private Long img;
    @ApiModelProperty(value = "视频文件id")
    private Long video;
    @ApiModelProperty(value = "学习任务文件id")
    private Long learningTask;
    @ApiModelProperty(value = "课后练习id")
    private Long homework;
}
