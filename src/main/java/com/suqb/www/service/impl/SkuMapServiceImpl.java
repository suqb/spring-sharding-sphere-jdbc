package com.suqb.www.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.suqb.www.domain.SkuMap;
import com.suqb.www.domain.dto.SkuMapDTO;
import com.suqb.www.repsitory.SkuMapRepository;
import com.suqb.www.service.SkuMapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * SKU映射服务实现类
 *
 * @author chenwushen
 * @date 2022/7/13
 */
@Service
@Slf4j
public class SkuMapServiceImpl implements SkuMapService
{

    @Resource
    private SkuMapRepository skuMapRepository;

    @Resource
    private MongoTemplate mongoTemplate;


    @Override
    public List<SkuMapDTO> getByPlatformSku(Collection<String> platformSkuList)
    {
        if (CollUtil.isEmpty(platformSkuList))
        {
            return ListUtil.empty();
        }
        // 获取映射列表
        List<SkuMap> skuMapList = mongoTemplate
                .find(Query.query(Criteria.where("platform_sku").in(platformSkuList)), SkuMap.class);

        if (CollUtil.isEmpty(platformSkuList))
        {
            return ListUtil.empty();
        }

        ArrayList<SkuMapDTO> dtoList = new ArrayList<>();

        for (SkuMap skuMap : skuMapList)
        {
            SkuMapDTO dto = new SkuMapDTO();
            BeanUtils.copyProperties(skuMap, dto);
            dtoList.add(dto);
        }

        // 转换映射列表
        return dtoList;
    }
}

