package com.zr.test.demo.model.dto;

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
@ApiModel(value = "课后教育查询入参", description = "")
public class OtherCourseQueryDTO extends PageTDO implements Serializable {

    private String name;
    @ApiModelProperty(value = "上下架", example = "0")
    private Integer status;
    @ApiModelProperty(value = "上传时间开始", example = "yyyy-MM-dd hh:mm:ss")
    private String startTime;
    @ApiModelProperty(value = "上传时间结束", example = "yyyy-MM-dd hh:mm:ss")
    private String endTime;
}
