package com.zr.test.demo.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class AppAndCaseVO {
    @ApiModelProperty(value = "id", example = "1213")
    private Long id;

    @ApiModelProperty(value = "软件名称", example = "1213")
    private String name;

    @ApiModelProperty(value = "log文件id", example = "1213")
    private Long logo;
    @ApiModelProperty(value = "图片文件id", example = "1213")
    private Long img;
    @ApiModelProperty(value = "简介", example = "1213")
    private String introduction;

    @ApiModelProperty(value = "类型", example = "1213")
    private Long type;

    @ApiModelProperty(value = "科目", example = "1213")
    private Object subject;

    @ApiModelProperty(value = "平台", example = "1213")
    private Object platform;

    @ApiModelProperty(value = "标签", example = "1213")
    private Object tags;

    @ApiModelProperty(value = "更新时间", example = "1213")
    private Date time;

    @ApiModelProperty(value = "是否通用 ", example = "1213")
    private Integer universal;

    @ApiModelProperty(value = "案例id", example = "1213")
    private Long caseid;

    @ApiModelProperty(value = "案例权重", example = "1213")
    private Integer caseorder;
}
