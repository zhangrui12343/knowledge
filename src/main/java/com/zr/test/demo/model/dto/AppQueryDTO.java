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
@ApiModel(value = "信课融合查询入参", description = "")
public class AppQueryDTO extends PageTDO implements Serializable {
    @ApiModelProperty(value = "软件名称", example = "1213")
    private String name;

    @ApiModelProperty(value = "软件类型", example = "1213")
    private Long type;

    @ApiModelProperty(value = "软件科目", example = "1213")
    private Long subject;

    @ApiModelProperty(value = "软件平台", example = "1213")
    private Long platform;

    @ApiModelProperty(value = "开始时间", example = "1213")
    private String startTime;
    @ApiModelProperty(value = "结束时间", example = "1213")
    private String endTime;
}
