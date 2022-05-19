package com.zr.test.demo.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class GeneralUserDTO extends PageTDO{

    @ApiModelProperty(value = "手机号", example = "1")
    private String phone;
    @ApiModelProperty(value = "是否禁用", example = "1")
    private Integer status;
}

