package com.zr.test.demo.model.vo;

import com.zr.test.demo.model.entity.SecondCategory;
import com.zr.test.demo.model.entity.Tag;
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
 * @since 2022-05-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="FirstCategory对象", description="")
public class FirstCategoryListVO implements Serializable {

    private static final long serialVersionUID=1L;
    @ApiModelProperty(value = "id", example = "1213")
    private Long id;
    @ApiModelProperty(value = "类型名称", example = "1213")
    private String name;
    @ApiModelProperty(value = "图片文件id", example = "1213")
    private Long img;

    @ApiModelProperty(value = "小分类", example = "1213")
    private List<SecondCategory> category;
    @ApiModelProperty(value = "标签", example = "1213")
    private List<Tag> tag;

}
