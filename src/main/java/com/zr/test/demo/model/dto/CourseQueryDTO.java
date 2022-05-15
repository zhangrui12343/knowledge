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
    @ApiModelProperty(value = "id", example = "1213")
    private String id;
    @ApiModelProperty(value = "课程名称", example = "1213")
    private String name;
    @ApiModelProperty(value = "学段", example = "1213")
    private Long xueduan;
    @ApiModelProperty(value = "年级", example = "1213")
    private Long grade;
    @ApiModelProperty(value = "学科", example = "1213")
    private Long subject;
    @ApiModelProperty(value = "册次", example = "1213")
    private Long books;
    @ApiModelProperty(value = "分类id", example = "1213")
    private Long secondCategoryId;
    @ApiModelProperty(value = "标签id", example = "1213")
    private Long tagId;
    @ApiModelProperty(value = "开始时间", example = "1213")
    private String startTime;
    @ApiModelProperty(value = "结束时间", example = "1213")
    private String endTime;

}
