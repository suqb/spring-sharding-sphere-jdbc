package com.suqb.www.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ProductEntity implements Entity
{
    @ExcelProperty(value = "SKU")
    private String sku;

    @ExcelProperty(value = "供应商编号")
    private String supplierNumber;

//    @ExcelProperty(value = "SKU价")
//    private Double skuPrice;
//
//    @ExcelProperty(value = "采购主管")
//    private String manager;

}
