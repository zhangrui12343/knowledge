package com.zr.test.demo.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AppOneVO {
    @ApiModelProperty(value = "id", example = "1213")
    private Long id;

    @ApiModelProperty(value = "软件名称", example = "1213")
    private String name;

    @ApiModelProperty(value = "log的地址", example = "1213")
    private String logo;
    @ApiModelProperty(value = "log文件id", example = "1213")
    private Long logoId;
    @ApiModelProperty(value = "图片地址", example = "1213")
    private String img;
    @ApiModelProperty(value = "图片文件id", example = "1213")
    private Long imgId;

    @ApiModelProperty(value = "简介", example = "1213")
    private String introduction;

    @ApiModelProperty(value = "类型", example = "1213")
    private Long type;

    @ApiModelProperty(value = "科目", example = "1213")
    private Long subject;

    @ApiModelProperty(value = "平台", example = "1213")
    private Long platform;

    @ApiModelProperty(value = "标签", example = "1213")
    private List<Long> tags;

    @ApiModelProperty(value = "上传时间", example = "1213")
    private Date time;

    @ApiModelProperty(value = "上下架", example = "1213")
    private Integer status;

    @ApiModelProperty(value = "上下架", example = "1213")
    private Integer universal;


}
