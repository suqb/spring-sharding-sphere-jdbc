package com.suqb.www.test;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ctc.wstx.evt.WstxEventReader;
import com.suqb.www.domain.Sales;
import com.suqb.www.domain.excel.SupplierEntity;
import com.suqb.www.listener.DataListener;
import com.suqb.www.util.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.apache.shardingsphere.sql.parser.sql.dialect.statement.mysql.segment.UserResourceSpecifiedLimitEnum;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SortTest
{
    @Data
//    @AllArgsConstructor
    public static class Message
    {
        private String departmentName;
        private String prefix;
        private Integer userId;

    }

    @Data
    public static class Message2{

        private String departmentName;
        private String prefix;
    }

    public static void main(String[] args) throws IOException
    {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());


        LocalDateTime localDateTime = LocalDateTime.of(2025, 3, 6, 9, 27, 13);

//        System.err.println(localDateTime!= null && Duration.between(localDateTime, LocalDateTime.now().minusHours(13)).toHours() > 12);
        System.err.println(localDateTime!= null && Duration.between(LocalDateTime.now().minusHours(13), LocalDateTime.now()).toHours() > 12);
//        System.err.println(timestamp!= null && Duration.between(timestamp.toLocalDateTime(), LocalDateTime.now().minusHours(13)).toHours() > 12);
        System.err.println(timestamp!= null && Duration.between(LocalDateTime.now().minusHours(13), LocalDateTime.now()).toHours() > 12);
        System.err.println(LocalDateTime.now().minusHours(13));


    }
}
