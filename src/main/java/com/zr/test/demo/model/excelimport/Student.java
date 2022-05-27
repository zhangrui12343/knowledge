package com.zr.test.demo.model.excelimport;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

@Data
@ExcelTarget("Student")
public class Student {

    @Excel(name = "学校名称")
      private String school;

    @Excel(name = "学生姓名")
    private String name;

    @Excel(name = "学籍号")
    private String studentNo;
}
