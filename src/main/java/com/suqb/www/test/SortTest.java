package com.suqb.www.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

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
