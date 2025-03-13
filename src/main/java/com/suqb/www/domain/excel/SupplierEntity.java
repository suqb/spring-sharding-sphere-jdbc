package com.suqb.www.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = false)
@Data
public class SupplierEntity implements Entity
{
//    @ExcelProperty(value = "采购单号")
//    private String s1;
//    @ExcelProperty(value = "采购人")
//    private String s2;
//    @ExcelProperty(value = "供应商[编号]")
//    private String s3;
//    @ExcelProperty(value = "物品总金额")
//    private BigDecimal s4;
//    @ExcelProperty(value = "运费")
//    private BigDecimal s5;
//    @ExcelProperty(value = "SKU")
//    private String s6;
//    @ExcelProperty(value = "商品名称")
//    private String s7;
//    @ExcelProperty(value = "采购数量")
//    private Integer s8;
//    @ExcelProperty(value = "产品分类")
//    private String categoryPath;
//    @ExcelProperty(value = "特性标签")
//    private String s10;
//    @ExcelProperty(value = "创建时间")
//    private String s11;
//    @ExcelProperty(value = "回货时间")
//    private String s12;
//    @ExcelProperty(value = "单价")
//    private String s13;
//    @ExcelProperty(value = "sourceaddr_province")
//    private String sourceAddrProvince;
//    @ExcelProperty(value = "sourceaddr_city")
//    private String sourceAddrCity;
//    @ExcelProperty(value = "main_product")
//    private String mainProduct;
//    @ExcelProperty(value = "category_id")
//    private Integer categoryId;
//    @ExcelProperty(value = "source_goods")
//    private String sourceGoods;


    //

//    @ExcelProperty(value = "系统订单号")
//    private String sysOrder;
//    @ExcelProperty(value = "平台订单号")
//    private String platformOrder;
//    @ExcelProperty(value = "别名sku")
//    private String aliasSku;
//    @ExcelProperty(value = "订单sku")
//    private String orderSku;
//    @ExcelProperty(value = "别名SKU开发员")
//    private String username;
//    @ExcelProperty(value = "别名SKU开发员工号")
//    private String jobNumber;
//
//
    @ExcelProperty(value = "SKU", index = 0)
    private String sku;
}
