package com.zr.test.demo.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ToolVO {
    @ApiModelProperty(value = "id", example = "1213")
    private Long id;

    @ApiModelProperty(value = "矩阵名称", example = "1213")
    private String name;

    @ApiModelProperty(value = "矩阵所属log路径", example = "1213")
    private List<String> logo;


}
