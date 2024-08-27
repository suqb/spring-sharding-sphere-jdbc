package com.suqb.www;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.suqb.www.domain.UserEntity;
import com.suqb.www.mapper.UserMapper;
import com.suqb.www.service.UserService;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class SpringShardingSphereJdbcApplicationTests
{

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserMapper userMapper;


    @Test
    void contextLoads()
    {
    }

    @Test
    public void insertData() {
        ArrayList<UserEntity> userList = new ArrayList<>();

        for (int i = 0; i < 100; i++)
        {
            userList.add(
                    new UserEntity().setName("username" + i).setSex(Math.random() > 0.5 ? "male" : "female")
            );
        }

        userService.saveBatch(userList);
    }

    @Test
    public void findById() {

        UserEntity user = userService.getById(1005556845852491777L);

        System.out.println(user);
    }

    @Test
    public void findByUserName() {

        UserEntity user = userService.getOne(
                new LambdaQueryWrapper<UserEntity>()
                        .eq(UserEntity::getName, "username30")
                        .last("limit 1")
        );

        System.out.println(user);
    }
    
    @Test
    public void testXml()
    {
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setName("t1");
        UserEntity userEntity = userMapper.selectByType(userEntity1);
        System.out.println(userEntity);
        System.out.println("=========================");
        userEntity1.setName("t2");
        UserEntity userEntity3 = userMapper.selectByType(userEntity1);
        System.out.println(userEntity3);
    }
}
