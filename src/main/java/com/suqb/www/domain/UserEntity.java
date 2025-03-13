package com.suqb.www.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @TableName tb_user
 */
@TableName(value = "tb_user")
@Data
@Accessors(chain = true)
public class UserEntity implements Serializable
{
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;


    private Integer productId;
    private Integer parentId;
    private Integer productBuyer;

    private Integer quantity;

    private Integer supplierId;
    private Integer categoryId;
    private Integer leastSixtySales;
    private Double refundRate60;

    private Double price;

    private String supplierNumber;
    private String categoryName;

    private String supplierName;
    private Integer priority;

    private String productSku;
    private Double salesPrice;




    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}