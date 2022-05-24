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
@ApiModel(value = "信课融合软件入参", description = "")
public class AppDTO implements Serializable {
    private Long id;
    @NotBlank
    @ApiModelProperty(value = "软件名称", example = "1213")
    private String name;

    @NotNull
    @ApiModelProperty(value = "logo文件id", example = "1213")
    private Long logo;

    @NotNull
    @ApiModelProperty(value = "图片id", example = "1213")
    private Long img;

    @NotBlank
    @ApiModelProperty(value = "描述", example = "1213")
    private String introduction;

    @NotNull
    @ApiModelProperty(value = "软件类型", example = "1213")
    private Integer type;

    @NotNull
    @ApiModelProperty(value = "软件科目", example = "1213")
    private Integer subject;

    @NotNull
    @ApiModelProperty(value = "软件平台", example = "1213")
    private Integer platform;

    @NotNull
    @ApiModelProperty(value = "软件标签", example = "1213")
    private List<Integer> tags;

    @NotNull
    @ApiModelProperty(value = "上下架", example = "1")
    private Integer status;
    @NotNull
    @ApiModelProperty(value = "是否通用", example = "1")
    private Integer universal;

}
