package com.suqb.www.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ProductSkuEntity implements Entity
{
    @ExcelProperty(value = "产品SKU")
    private String sku;

}
