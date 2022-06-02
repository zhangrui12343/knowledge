package com.zr.test.demo.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class ToolApps {
    private static final long serialVersionUID=1L;

    private String tool;

    private Long id;

    private String name;

    private Long logo;

    private Long img;

    private String introduction;

    private Long type;

    private Long subject;

    private Long platform;

    private String tags;

    private Integer status;

    private Integer universal;

    private Date time;

}
