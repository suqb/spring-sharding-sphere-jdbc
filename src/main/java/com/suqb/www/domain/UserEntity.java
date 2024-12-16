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

    private String platformOrderId;
    private String productSku;
    private Integer saleQuantity;
    private Integer parentId;
    private String categoryName;
    private Integer categoryId;

    private String productTitle;
    private String productFeature;
    private Integer listingStatus;
    private Timestamp completionDate;

    private Integer yesterdaySales;
    private Integer leastThirtySales;
    private Integer leastThreeSales, leastSevenSales, leastFifteenSales;
    private Integer leastSixtySales;
    private Integer historySales;


    private Integer inTransitStock;
    private Integer stock;
    private Integer stockout;


    private Integer priority;
    private BigDecimal purchasePrice;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}