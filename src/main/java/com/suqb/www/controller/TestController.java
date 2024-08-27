package com.suqb.www.controller;

import com.suqb.www.domain.request.TestRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/index")
public class TestController
{
    @PostMapping("/post")
    public Map<String, Object> test(@RequestBody Map<String, Object> requestMap)
    {
        return new HashMap<String, Object>() {{
            put("code", 200);
            put("businessId", 1920);
            put("status", "success");
            put("request", requestMap.get("requestId"));
        }};
    }
    
    @GetMapping("/test")
    public Map<String, Object> test(TestRequest data) {
        return new HashMap<String, Object>() {{
            put("code", 200);
        }};
    }
}
