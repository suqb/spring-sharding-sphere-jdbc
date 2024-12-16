package com.suqb.www.mapper;

import com.suqb.www.domain.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
* @author wumingjie
* @description 针对表【tb_user】的数据库操作Mapper
* @createDate 2024-06-06 19:13:29
* @Entity generator.domain.UserEntity
*/
public interface UserMapper extends BaseMapper<UserEntity> {

    UserEntity selectByType(UserEntity userEntity);

    List<UserEntity> querySkuFeatureBySku(List<String> skuList);

    List<UserEntity> queryBySql(String sql);
}




