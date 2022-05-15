package com.zr.test.demo.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class GeneralUserDTO extends PageTDO{

    @ApiModelProperty(value = "状态", example = "1")
    @NotNull(message="状态不能为空")
    private Integer status;


    @ApiModelProperty(value = "手机号", example = "1")
    private String phone;
}

