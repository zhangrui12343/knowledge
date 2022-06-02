package com.zr.test.demo.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
public class FirstCategoryVO implements Serializable {

    private static final long serialVersionUID=1L;
    @ApiModelProperty(value = "id", example = "1213")
    private Long id;
    @ApiModelProperty(value = "类型名称", example = "1213")
    private String name;
    @ApiModelProperty(value = "图片文件路径", example = "1213")
    private String img;
    @ApiModelProperty(value = "图片文件id", example = "1213")
    private Long imgId;
    @ApiModelProperty(value = "权重", example = "1213")
    private Integer order;
    @ApiModelProperty(value = "分类名称", example = "1213")
    private String category;
    @ApiModelProperty(value = "标签名称", example = "1213")
    private String tag;

}
