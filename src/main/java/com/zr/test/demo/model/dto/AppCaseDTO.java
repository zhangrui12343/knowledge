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
@ApiModel(value = "信课融合案例入参", description = "")
public class AppCaseDTO implements Serializable {
    private Long id;
    @NotBlank
    @ApiModelProperty(value = "软件名称", example = "1213")
    private String appName;
    @NotBlank
    @ApiModelProperty(value = "软件名称", example = "1213")
    private String appId;
    @NotBlank
    @ApiModelProperty(value = "案例名称", example = "1213")
    private String caseName;
    @NotNull
    @ApiModelProperty(value = "视频文件id", example = "1213")
    private Long video;

    @NotBlank
    @ApiModelProperty(value = "软件介绍", example = "1213")
    private String appIntroduction;

    @NotBlank
    @ApiModelProperty(value = "软件介绍", example = "1213")
    private String feature;

    @NotNull
    @ApiModelProperty(value = "上下架", example = "1")
    private Integer status;

    @NotNull
    @ApiModelProperty(value = "权重", example = "1")
    private Integer order;

}
