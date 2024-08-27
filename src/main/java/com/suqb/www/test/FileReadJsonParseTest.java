package com.suqb.www.test;

import com.alibaba.fastjson2.JSON;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileReadJsonParseTest
{
    @Data
    public static class ClothingSize
    {
        private String region;
        private String sizeS;
        private String sizeM;
        private String sizeL;
        private String sizeXL;
        private String sizeXXL;
        private String sizeXXXL;
        private String sizeXXXXL;
        private String sizeXXXXXL;
        private static String publicErrand;
    }
    
    public static void main(String[] args) throws IOException
    {
        String path = "F:\\workspace\\java\\spring-sharding-sphere-jdbc\\spring-sharding-sphere-jdbc\\src\\main\\resources\\res.json";

        String json = new String(Files.readAllBytes(Paths.get(path)));

        List<Object> objects = JSON.parseArray(json, ClothingSize.class);

        System.out.println(JSON.toJSON(objects));
    }
    
}