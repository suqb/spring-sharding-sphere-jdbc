package com.suqb.www.constant;

import java.util.Arrays;

public enum WalmartSiteEnum
{
    US("US", "美国 US"),
    CA("CA", "加拿大 CA"),
    MX("MX", "墨西哥 MX");

    private String code;
    private String display;

    private WalmartSiteEnum(String code, String display)
    {
        this.code = code;
        this.display = display;
    }

    public static WalmartSiteEnum build(String code)
    {
        return (WalmartSiteEnum) Arrays.stream(values()).filter((type) ->
        {
            return type.code.equalsIgnoreCase(code);
        }).findAny().orElse((WalmartSiteEnum) null);
    }

    public String code()
    {
        return this.code;
    }

    public String display()
    {
        return this.display;
    }
}

