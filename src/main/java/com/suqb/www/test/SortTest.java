package com.suqb.www.test;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.math3.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class SortTest
{
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Product
    {
        Integer id;
    }

    public static final String PRODUCT_IMAGE_FOLDER = "product/ftp";

    public static void main(String[] args)
    {

        String s = "{\n" +
                "  \"1\": false,\n" +
                "  \"2\": true,\n" +
                "  \"3\": true,\n" +
                "  \"4\": true,\n" +
                "  \"5\": true,\n" +
                "  \"6\": true,\n" +
                "  \"7\": true,\n" +
                "  \"8\": true,\n" +
                "  \"9\": true,\n" +
                "  \"10\": true,\n" +
                "  \"11\": true\n" +
                "}";

        HashMap<Integer, Boolean> o = JSON.parseObject(s, new TypeReference<HashMap<Integer, Boolean>>()
        {
        });


        System.err.println(o);

    }
}
