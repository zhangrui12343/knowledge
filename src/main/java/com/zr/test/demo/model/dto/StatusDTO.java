package com.zr.test.demo.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class StatusDTO extends UserIdDTO{

    @ApiModelProperty(value = "上下架", example = "1")
    @NotNull(message="上下架不能为空")
    private Integer status;

}
