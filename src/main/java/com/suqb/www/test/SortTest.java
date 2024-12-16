package com.suqb.www.test;

import lombok.AllArgsConstructor;
import lombok.Data;

public class SortTest
{
    @Data
    @AllArgsConstructor
    public static class Message
    {
        private String departmentName;
        private String prefix;
    }

    public static void main(String[] args)
    {
        String url = "http://183.62.143.166:28099/image/research/images/20241118/fAFWCpwra60Uay35KIWeWUkAOXtbwIkW.jpg";


        int index = url.indexOf("research/images");

        if (index != -1)
        {
            url = url.substring(index);
        }

        System.out.println(url);
    }
}
