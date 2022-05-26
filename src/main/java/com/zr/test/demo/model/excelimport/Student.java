package com.zr.test.demo.model.excelimport;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

@Data
@ExcelTarget("Student")
public class Student {

    @Excel(name = "school")
    @ExcelEntity(name = "school")
    private String school;

    @Excel(name = "name")
    private String name;

    @Excel(name = "studentNo")
    private String studentNo;
}
