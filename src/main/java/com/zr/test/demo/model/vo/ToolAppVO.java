package com.zr.test.demo.model.vo;

import com.zr.test.demo.model.entity.App;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ToolAppVO {

    @ApiModelProperty(value = "矩阵名称", example = "1213")
    private String name;

    @ApiModelProperty(value = "矩阵关联的app", example = "1213")
    private List<App> apps;

}
