package com.suqb.www.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suqb.www.domain.UserEntity;
import com.suqb.www.domain.excel.DieOutExcelEntity;
import com.suqb.www.listener.DataListener;
import com.suqb.www.mapper.UserMapper;
import com.suqb.www.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
* @author wumingjie
* @description 针对表【tb_user】的数据库操作Service实现
* @createDate 2024-06-06 19:13:29
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity>
    implements UserService
{

    public void th()
    {
        new DataListener<DieOutExcelEntity>(new ArrayList<>()).th();
    }
}




