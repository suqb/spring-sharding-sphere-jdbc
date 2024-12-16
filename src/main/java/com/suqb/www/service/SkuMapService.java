package com.suqb.www.service;

import com.suqb.www.domain.dto.PageDTO;
import com.suqb.www.domain.dto.SkuMapDTO;
import com.suqb.www.domain.query.SkuMapQuery;

import java.util.Collection;
import java.util.List;

/**
 * SKU映射服务接口
 *
 * @author chenwushen
 * @date 2022/7/13
 */
public interface SkuMapService
{

    /**
     * 根据平台SKU获取映射列表
     *
     * @param platformSkuList
     * @return
     */
    List<SkuMapDTO> getByPlatformSku(Collection<String> platformSkuList);
}
