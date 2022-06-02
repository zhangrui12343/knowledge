package com.zr.test.demo.model.pojo;

import com.zr.test.demo.model.entity.AfterCourse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author zr
 * @since 2022-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "首页课后服务", description = "")
public class AfterCourseInIndex implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "大类型id")
    private Long id;
    @ApiModelProperty(value = "大类型名称")
    private String name;
    @ApiModelProperty(value = "某个类型下的课后服务")
    private List<AfterCourseOne> afterCourseOne;

    @Data
    public static class AfterCourseOne implements Serializable {
        private static final long serialVersionUID = 1L;
        private String name;
        private Long img;
        private List<String> tags;
        private List<String> categories;
        private Long count;
        private String date;
    }
}
