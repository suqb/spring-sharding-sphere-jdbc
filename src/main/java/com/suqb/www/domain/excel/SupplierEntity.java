package com.suqb.www.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = false)
@Data
public class SupplierEntity implements Entity
{
    @ExcelProperty(value = "下单供应商店铺名称")
    private String supName;

    @ExcelProperty(value = "名称对应系统编号")
    private String supNumber;

    @ExcelProperty(value = "对应系统编号月采购金额")
    private String purchasePrice;

    @ExcelProperty(value = "对应系统编号上架sku个数")
    private Integer skuCount;

    @ExcelProperty(value = "对应系统编号状态")
    private String status;
}
