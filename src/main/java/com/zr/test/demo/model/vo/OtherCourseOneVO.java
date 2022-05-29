package com.zr.test.demo.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author zr
 * @since 2022-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "课后教育", description = "")
public class OtherCourseOneVO implements Serializable {
    @ApiModelProperty(value = "id", example = "1213")
    private Long id;
    @ApiModelProperty(value = "课后教育服务名称", example = "1213")
    private String name;
    @ApiModelProperty(value = "图片的地址", example = "1213")
    private String img;
    @ApiModelProperty(value = "课后教育类型", example = "1213")
    private Long type;
    @ApiModelProperty(value = "分类", example = "1213")
    private Long category;
    @ApiModelProperty(value = "标签", example = "1213")
    private List<Long> tags;
    @ApiModelProperty(value = "视频地址", example = "1213")
    private List<String> videos;
    @ApiModelProperty(value = "文档地址", example = "1213")
    private List<String> docs;
    @ApiModelProperty(value = "视频id", example = "1213")
    private List<Long> videoIds;
    @ApiModelProperty(value = "文档id", example = "1213")
    private List<Long> docsIds;
    @ApiModelProperty(value = "课程简介", example = "1213")
    private String description;
    private Integer status;

}
