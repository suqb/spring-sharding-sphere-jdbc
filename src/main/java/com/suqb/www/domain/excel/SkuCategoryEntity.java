package com.suqb.www.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = false)
@Data
public class SkuCategoryEntity implements Entity
{
    @ExcelProperty(value = "SKU")
    private String sku;

    @ExcelProperty(value = "分类名称")
    private String categoryName;

//    @ExcelProperty(value = "一级类目")
//    private String classify1;
//    @ExcelProperty(value = "二级类目")
//    private String classify2;
//    @ExcelProperty(value = "三级类目")
//    private String classify3;
//    @ExcelProperty(value = "四级类目")
//    private String classify4;
}
