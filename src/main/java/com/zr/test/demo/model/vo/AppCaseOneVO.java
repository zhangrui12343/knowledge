package com.zr.test.demo.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@ApiModel(value = "信课融合案例出参", description = "")
public class AppCaseOneVO implements Serializable {
    private Long id;
    @ApiModelProperty(value = "软件名称", example = "1213")
    private String appName;
    @ApiModelProperty(value = "软件名称", example = "1213")
    private String appId;
    @ApiModelProperty(value = "案例名称", example = "1213")
    private String caseName;
    @ApiModelProperty(value = "视频文件路径", example = "1213")
    private String video;
    @ApiModelProperty(value = "视频文件id", example = "1213")
    private Long videoId;
    @ApiModelProperty(value = "软件介绍", example = "1213")
    private String appIntroduction;

    @ApiModelProperty(value = "软件介绍", example = "1213")
    private String feature;

    @ApiModelProperty(value = "上下架", example = "1")
    private Integer status;

    @ApiModelProperty(value = "权重", example = "1")
    private Integer order;

}
