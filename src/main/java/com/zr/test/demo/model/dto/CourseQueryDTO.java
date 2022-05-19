package com.zr.test.demo.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

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

    @ApiModelProperty(value = "学段", example = "1213")
    private Long xueduan;
    @ApiModelProperty(value = "年级", example = "1213")
    private Long grade;
    @ApiModelProperty(value = "学科", example = "1213")
    private Long subject;
    @ApiModelProperty(value = "开始时间", example = "1213")
    private String startTime;
    @ApiModelProperty(value = "结束时间", example = "1213")
    private String endTime;

}
