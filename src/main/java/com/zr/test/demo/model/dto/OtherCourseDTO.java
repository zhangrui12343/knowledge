package com.zr.test.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OtherCourseDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id", example = "1213")
    private Long id;
    @NotBlank(message = "name 不能为空")
    @ApiModelProperty(value = "名称", example = "1213")
    private String name;
    @NotNull(message = "img 不能为空")
    @ApiModelProperty(value = "图片id", example = "1213")
    private Long img;
    @NotNull(message = "type 不能为空")
    @ApiModelProperty(value = "类型id", example = "1213")
    private Long type;

    @ApiModelProperty(value = "分类id", example = "1213")
    private List<Long> categories;

    @ApiModelProperty(value = "标签id", example = "1213")
    private List<Long> tags;
    @ApiModelProperty(value = "视频文件id", example = "1213")
    private List<Long> videos;
    @ApiModelProperty(value = "文档文件id", example = "1213")
    private List<Long> docs;
    @NotBlank(message = "description 不能为空")
    @ApiModelProperty(value = "描述", example = "1213")
    private String description;
    @NotNull(message = "status 不能为空")
    @ApiModelProperty(value = "上下架", example = "1213")
    private Integer status;

}
