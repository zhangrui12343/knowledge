package com.zr.test.demo.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class AppStatusDTO extends UserIdDTO{

    @ApiModelProperty(value = "上下架", example = "1")
    private Integer status;
    @ApiModelProperty(value = "是否通用", example = "1")
    private Integer universal;
}
