package com.zr.test.demo.model.dto;

import io.swagger.annotations.ApiModel;
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
@ApiModel(value = "课后、专题、教师研修入参", description = "")
public class OtherCourseListDTO extends PageTDO implements Serializable {

    @ApiModelProperty(value = "大分类", example = "1213")
    private Long type;
    @ApiModelProperty(value = "小分类", example = "1213")
    private Long category;
    @ApiModelProperty(value = "标签", example = "1213")
    private Long tag;
}
