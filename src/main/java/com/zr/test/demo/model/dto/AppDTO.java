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
    @NotBlank(message = "name 不能为空")
    @ApiModelProperty(value = "软件名称", example = "1213")
    private String name;

    @NotNull(message = "logo 不能为空")
    @ApiModelProperty(value = "logo文件id", example = "1213")
    private Long logo;

    @NotNull(message = "img 不能为空")
    @ApiModelProperty(value = "图片id", example = "1213")
    private Long img;

    @NotBlank(message = "introduction 不能为空")
    @ApiModelProperty(value = "描述", example = "1213")
    private String introduction;

    @NotNull(message = "type 不能为空")
    @ApiModelProperty(value = "软件类型", example = "1213")
    private Long type;

    @NotNull(message = "subject 不能为空")
    @ApiModelProperty(value = "软件科目", example = "1213")
    private  List<Long> subject;

    @NotNull(message = "platform 不能为空")
    @ApiModelProperty(value = "软件平台", example = "1213")
    private  List<Long> platform;

    @NotNull(message = "tags 不能为空")
    @ApiModelProperty(value = "软件标签", example = "1213")
    private List<Long> tags;

    @NotNull(message = "status 不能为空")
    @ApiModelProperty(value = "上下架", example = "1")
    private Integer status;

    @NotNull(message = "universal 不能为空")
    @ApiModelProperty(value = "是否通用", example = "1")
    private Integer universal;

}
