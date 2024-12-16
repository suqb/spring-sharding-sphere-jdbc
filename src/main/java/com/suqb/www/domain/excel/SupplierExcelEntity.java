package com.suqb.www.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class SupplierExcelEntity implements Entity
{
    @ExcelProperty(value = "采购员")
    private String sku;

    @ExcelProperty(value = "供应商编号")
    private String supplierNumber;
}
