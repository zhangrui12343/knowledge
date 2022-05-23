package com.zr.test.demo.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zr
 * @since 2022-05-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="FirstCategory对象", description="")
public class FirstCategoryVO implements Serializable {

    private static final long serialVersionUID=1L;

    private Long id;

    private String name;

    private String img;

    private Long imgId;

    private Integer order;

    private String category;

    private String tag;

}
