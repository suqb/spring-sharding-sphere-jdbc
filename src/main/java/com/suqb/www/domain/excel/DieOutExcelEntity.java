package com.suqb.www.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = false)
@Data
public class DieOutExcelEntity implements Entity
{
    @ExcelProperty(value = "唯一标识")
    private Integer id;

    @ExcelProperty(value = "sku")
    private String sku;

    @ExcelProperty(value = "单价")
    private BigDecimal price;

    @ExcelProperty(value = "30天销量")
    private Integer sales30;

    @ExcelProperty(value = "60天销量")
    private Integer sales60;

    @ExcelProperty(value = "90天销量")
    private Integer sales90;

    @ExcelProperty(value = "历史销量")
    private Integer salesHis;

    @ExcelProperty(value = "开发员唯一标识")
    private Integer devId;

    @ExcelProperty(value = "开发员")
    private String dev;

    @ExcelProperty(value = "开发部门")
    private String dept;

}
