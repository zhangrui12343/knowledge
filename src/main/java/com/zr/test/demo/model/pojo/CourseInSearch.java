package com.zr.test.demo.model.pojo;

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
 * @since 2022-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "搜索课程返回的参数", description = "")
public class CourseInSearch implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "课程id")
    private Long id;

    @ApiModelProperty(value = "课程名称")
    private String name;

    @ApiModelProperty(value = "课程封面")
    private Long img;

    @ApiModelProperty(value = "授课老师")
    private String teacher;

    @ApiModelProperty(value = "课程描述")
    private String description;

    @ApiModelProperty(value = "课程大分类名称")
    private String category;

    @ApiModelProperty(value = "课程小分类名称")
    private String type;

    @ApiModelProperty(value = "课程标签名称")
    private String tag;

}
