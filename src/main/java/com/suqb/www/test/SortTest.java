package com.suqb.www.test;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

public class SortTest
{
    @Data
    @AllArgsConstructor
    static class Message
    {
        private LocalDate time;
    }

    public static void main(String[] args)
    {
        
    }
}
