package com.suqb.www.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
public class ProductEntity implements Entity
{
    @ExcelProperty(value = "速卖通近30天动销SKU")
    private String productSku;

    @ExcelProperty(value = "供应商编号")
    private String supplierNumber;
}
