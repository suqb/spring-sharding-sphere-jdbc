package com.suqb.www.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = false)
@Data
public class DieOutExcelEntity implements Entity
{
    @ExcelProperty(value = "供应商-1")
    private Integer id;

    @ExcelProperty(value = "供应商-2")
    private String sku;

    @ExcelProperty(value = "供应商-3")
    private BigDecimal price;

    @ExcelProperty(value = "供应商-4")
    private Integer sales30;

    @ExcelProperty(value = "供应商-5")
    private Integer sales60;

    @ExcelProperty(value = "供应商-6")
    private Integer sales90;

    @ExcelProperty(value = "供应商-7")
    private Integer salesHis;

    @ExcelProperty(value = "供应商-8")
    private Integer devId;

    @ExcelProperty(value = "供应商-9")
    private String dev;

}
