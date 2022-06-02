package com.zr.test.demo.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "课后、专题、教师研修出参", description = "")
public class OtherCourseListVO {
    @ApiModelProperty(value = "分类id", example = "1213")
    private Long categoryId;
    @ApiModelProperty(value = "分类img id", example = "1213")
    private Long categoryImg;
    @ApiModelProperty(value = "分类标题", example = "1213")
    private String categoryName;

    @ApiModelProperty(value = "前三个资源")
    private List<OtherCourseWebVO> dos;

}
