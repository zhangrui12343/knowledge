package com.zr.test.demo.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class PageTDO {

    @ApiModelProperty(value = "页数", example = "1")
    @Min(1)
    @NotNull(message = "page 不能为空")
    private Integer page;


    @ApiModelProperty(value = "每页大小", example = "1")
    @Min(1)
    @NotNull(message = "pageSize 不能为空")
    private Integer pageSize;
}
