package com.zr.test.demo.model.pojo;

import com.zr.test.demo.model.entity.Leaderboard;
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
@ApiModel(value="首页排行榜", description="")
public class LeaderInIndex implements Serializable {

    private static final long serialVersionUID=1L;
    @ApiModelProperty(value = "学科排行榜")
    private List<Leaderboard> subject;
    @ApiModelProperty(value = "学校排行榜")
    private List<Leaderboard> school;
    @ApiModelProperty(value = "教师排行榜")
    private List<Leaderboard> teacher;

}
