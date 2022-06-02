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
@ApiModel(value="教育分类", description="")
public class FirstCategoryDTO implements Serializable {

    private static final long serialVersionUID=1L;
    @ApiModelProperty(value = "id", example = "1213")
    private Long id;
    @NotBlank(message = "类型名称不能为空")
    @ApiModelProperty(value = "类型名称", example = "1213")
    private String name;
    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "类型不能为空", example = "0:课后教育,1:专题教育,:2:教师研修")
    private Integer type;
    @NotNull(message = "图片不能为空")
    @ApiModelProperty(value = "图片文件id", example = "1213")
    private Long img;
    @ApiModelProperty(value = "权重", example = "1213")
    private Integer order;
    @NotNull(message = "分类不能为空")
    @ApiModelProperty(value = "分类", example = "1213")
    private List<Long> category;
    @ApiModelProperty(value = "标签id", example = "1213")
    @NotNull(message = "标签不能为空")
    private List<Long> tag;

}
