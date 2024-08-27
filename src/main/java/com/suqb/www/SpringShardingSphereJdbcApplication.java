package com.suqb.www;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.suqb.www.mapper")
public class SpringShardingSphereJdbcApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(SpringShardingSphereJdbcApplication.class, args);
    }

}
