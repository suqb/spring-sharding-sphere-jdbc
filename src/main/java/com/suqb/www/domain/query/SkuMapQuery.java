package com.suqb.www.domain.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * SKU映射查询类
 *
 * @author chenwushen
 * @date 2022/7/13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SkuMapQuery extends PageQuery
{
    /**
     * 序列化标识
     */
    private static final long serialVersionUID = 1L;

    /**
     * 系统SKU等于查询
     */
    private String systemSku;

    /**
     * 系统SKU模糊查询
     */
    private String likeSystemSku;

    /**
     * 系统SKU列表查询
     */
    private List<String> systemSkuList;

    /**
     * 平台SKU等于查询
     */
    private String platformSku;

    /**
     * 平台SKU模糊查询
     */
    private String likePlatformSku;

    /**
     * 平台SKU列表查询
     */
    private List<String> platformSkuList;

    /**
     * 创建人等于查询
     */
    private Integer createBy;

    /**
     * 创建人列表查询
     */
    private List<Integer> createByList;

    /**
     * 授权创建人列表
     */
    private List<Integer> authCreateByList;
}

