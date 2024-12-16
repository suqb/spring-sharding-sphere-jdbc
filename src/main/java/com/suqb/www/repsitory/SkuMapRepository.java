package com.suqb.www.repsitory;

import com.suqb.www.domain.SkuMap;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * SKU映射存储接口
 *
 * @author chenwushen
 * @date 2022/7/13
 */
public interface SkuMapRepository extends MongoRepository<SkuMap, String>
{
}

