package com.suqb.www.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ExportExcelEntity implements Entity
{
//    @ExcelProperty(value = "product_sku")
//    private String sku;
//
//    @ExcelProperty(value = "category_name")
//    private String category;
//
//    @ExcelProperty(value = "supplier_number")
//    private String number;
//
//    @ExcelProperty(value = "缺货")
//    private String stockOut;
//
//    @ExcelProperty(value = "7天销量")
//    private String sale7;
//
//    @ExcelProperty(value = "30天销量")
//    private String sale30;
//
//    @ExcelProperty(value = "历史销量")
//    private String saleHis;
//
//    @ExcelProperty(value = "版型编码")
//    private String cloth;
//
//    @ExcelProperty(value = "年份")
//    private String year;
//
//    @ExcelProperty(value = "月份")
//    private String month;
//
//    @ExcelProperty(value = "下架")
//    private String off;
//
//    @ExcelProperty(value = "维度")
//    private String repeat;
//
//    @ExcelProperty(value = "特性标签")
//    private String feature;

//    @ExcelProperty(value = "主SKU")
//    private String sku;
//
//    @ExcelProperty(value = "采购员列表")
//    private String buyer;
//
//    @ExcelProperty(value = "供应商编号列表")
//    private String supplierNo;

    @ExcelProperty(value = "SKU")
    private String sku;

    @ExcelProperty(value = "供应商编号")
    private String supplierNumber;

    @ExcelProperty(value = "采购地址")
    private String address;

    @ExcelProperty(value = "7天销量")
    private Integer sales7;

//    @ExcelProperty(value = "供应商名称")
//    private String supplierName;
//
//    @ExcelProperty(value = "分类")
//    private String categoryPath;
//
//    @ExcelProperty(value = "入库金额")
//    private Double amount;
}
