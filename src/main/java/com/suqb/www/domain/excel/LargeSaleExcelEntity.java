package com.suqb.www.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = false)
@Data
public class LargeSaleExcelEntity implements Entity
{
    @ExcelProperty(value = "SKU")
    private String sku;
//    @ExcelProperty(value = "24-28号销量")
//    private Long largeSale;
    @ExcelProperty(value = "品名")
    private String title;
    @ExcelProperty(value = "特性标签")
    private String label;
    @ExcelProperty(value = "一级类目")
    private String classify1;
    @ExcelProperty(value = "二级类目")
    private String classify2;
    @ExcelProperty(value = "三级类目")
    private String classify3;
    @ExcelProperty(value = "昨日销量")
    private Integer sales;
    @ExcelProperty(value = "3天销量")
    private Integer sales3;
    @ExcelProperty(value = "7天销量")
    private Integer sales7;
    @ExcelProperty(value = "15天销量")
    private Integer sales15;
    @ExcelProperty(value = "30天销量")
    private Integer sales30;
    @ExcelProperty(value = "60天销量")
    private Integer sales60;
    @ExcelProperty(value = "历史销量")
    private Integer salesHis;
    @ExcelProperty(value = "单价")
    private BigDecimal price;
    @ExcelProperty(value = "上架状态")
    private String listingStatus;
    @ExcelProperty(value = "在途库存")
    private Integer inTransitStock;
    @ExcelProperty(value = "剩余库存")
    private Integer stock;
    @ExcelProperty(value = "缺货数量")
    private Integer stockOut;
    @ExcelProperty(value = "上架时长")
    private String completionDate;
}
