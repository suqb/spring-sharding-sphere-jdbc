package com.suqb.www.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ProductLevelStaticsEntity
{
    @ExcelProperty(value = "父SKU")
    private String parentSku;

    @ExcelProperty(value = "产品大类")
    private String category;

    @ExcelProperty(value = "特性标签")
    private String label;

    @ExcelProperty(value = "上架时间")
    private String competedDate;

    @ExcelProperty(value = "30天销量")
    private Integer sale30;

    @ExcelProperty(value = "60天销量")
    private Integer sale60;

    @ExcelProperty(value = "历史销量")
    private Integer historySales;

    @ExcelProperty(value = "30天退货率")
    private String refundRate30;

    @ExcelProperty(value = "60天退货率")
    private String refundRate60;

    @ExcelProperty(value = "累计退货率")
    private String refundRateTotal;
}
