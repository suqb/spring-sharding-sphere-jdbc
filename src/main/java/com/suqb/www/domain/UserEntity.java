package com.suqb.www.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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

    private String productSku;
    private String parentSku;
    private String shopName;
    private Double purchasePrice;
    private Integer quantity;
    private Integer leastThirtySales;
    private Integer historySales;

    private Integer supplierId;
    private Integer productId;
    private String supplierNumber;
    private String supplierName;
    private Integer paymentMethod;

    private Integer productBuyer;
    private Integer priority;
    private Integer userId;
    private Integer supervisor;
    private Integer isBlackList;

    private Double salesAmount;
    private Double refundAmount;
    private LocalDateTime completionDate;

    private String json;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}