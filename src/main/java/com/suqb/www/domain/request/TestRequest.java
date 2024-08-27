package com.suqb.www.domain.request;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class TestRequest
{
    private Map<String, TestMessageRequest> request = new HashMap<>();
}
