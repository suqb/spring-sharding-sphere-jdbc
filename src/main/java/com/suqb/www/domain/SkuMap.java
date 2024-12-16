package com.suqb.www.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * SKU映射实体类
 *
 * @author chenwushen
 * @date 2022/7/13
 */
@Data
@Document("sku_map")
public class SkuMap
{
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 系统SKU
     */
    @Field("system_sku")
    private String systemSku;

    /**
     * 平台SKU
     */
    @Field("platform_sku")
    private String platformSku;

    /**
     * 平台SKU大写
     */
    @Field("platform_sku_upper")
    private String platformSkuUpper;

    /**
     * 创建人
     */
    @Field("create_by")
    private Integer createBy;

    /**
     * 创建时间
     */
    @Field("create_time")
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    @Field("update_by")
    private Integer updateBy;

    /**
     * 修改时间
     */
    @Field("update_time")
    private LocalDateTime updateTime;
}
