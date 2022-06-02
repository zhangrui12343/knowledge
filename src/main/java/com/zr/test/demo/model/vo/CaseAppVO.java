package com.zr.test.demo.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CaseAppVO {
    private static final long serialVersionUID=1L;

    private Long caseid;
    private Integer caseorder;
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
