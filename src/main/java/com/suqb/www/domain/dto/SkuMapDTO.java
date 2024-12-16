package com.suqb.www.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * SKU映射传输类
 *
 * @author chenwushen
 * @date 2022/7/13
 */
@Data
public class SkuMapDTO implements Serializable
{
    /**
     * 序列化标识
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 系统SKU
     */
    private String systemSku;

    /**
     * 平台SKU
     */
    private String platformSku;

    /**
     * 平台SKU大写
     */
    private String platformSkuUpper;

    /**
     * 创建人
     */
    private Integer createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private Integer updateBy;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}

