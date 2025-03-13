package com.suqb.www;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.mapper.Mapper;
import com.suqb.www.domain.UserEntity;
import com.suqb.www.mapper.UserMapper;
import com.suqb.www.service.SkuMapService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class SearchNoProductSupplierTest
{
    @Autowired
    private UserMapper userMapper;


    @Test
    void contextLoads()
    {
    }

    @Test
    public void query()
    {
        // 获取灰名单供应商
        List<Integer> supplierIds = userMapper.queryBySql("SELECT supplier_id FROM t_supplier WHERE is_blackList = 3 AND is_delete = 0").stream().map(UserEntity::getSupplierId).collect(Collectors.toList());

        // 获取供应商产品
        List<UserEntity> productSupplierList = userMapper.queryBySql("SELECT supplier_id, product_id, priority FROM t_product_supplier WHERE product_id IN (SELECT product_id FROM t_product_supplier WHERE supplier_id IN (SELECT supplier_id FROM t_supplier WHERE is_blackList = 3 AND is_delete = 0))");

        // 产品分组
        Map<Integer, List<UserEntity>> productGroup = productSupplierList.stream().collect(Collectors.groupingBy(UserEntity::getProductId));

        // 供应商分组
        Map<Integer, List<UserEntity>> supplierGroup = productSupplierList.stream().collect(Collectors.groupingBy(UserEntity::getSupplierId));

        // 黑名单列表
        ArrayList<Integer> backList = new ArrayList<>();

        for (Integer supplierId : supplierIds)
        {
            boolean flag = true;

            // 获取产品列表
            List<UserEntity> productList = supplierGroup.get(supplierId);

            if (CollUtil.isNotEmpty(productList))
            {
                List<Integer> productIdList = productList.stream().map(UserEntity::getProductId).collect(Collectors.toList());

                for (Integer productId : productIdList)
                {
                    // 获取该产品的所有供应商
                    List<UserEntity> supplierList = productGroup.get(productId);

                    if (CollUtil.isNotEmpty(supplierList))
                    {
                        // 如果只有一个供应商则必是当前供应商有产品不能拉黑
                        if (supplierList.size() == 1)
                        {
                            flag = false;
                        }
                        else
                        {
                            // 获取优先级最小的供应商
                            Optional<UserEntity> min = supplierList.stream().peek(e -> {
                                if (e.getPriority() == null)
                                {
                                    e.setPriority(0);
                                }
                            }).min(Comparator.comparing(UserEntity::getPriority));

                            // 如果存在并且是当前供应商则不能拉黑
                            if (min.isPresent() && min.map(UserEntity::getSupplierId).orElse(0).equals(supplierId))
                            {
                                flag = false;
                            }
                        }
                    }
                }
            }

            if (flag)
            {
                backList.add(supplierId);
            }
        }
        System.err.println(backList.stream().map(String::valueOf).collect(Collectors.joining(",")));
    }
}
