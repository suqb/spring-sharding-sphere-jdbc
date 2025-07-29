package com.suqb.www.constant;

/**
 * 供应商黑名单状态
 *
 * @author 胡明
 * @version Bessky V100R001 2020年12月08日
 * @since Bessky V100R001C00
 */
public enum SupplierBlackListType
{
    /**
     * 可以正常下单与上新产品
     */
    NORMAL("0", "正常"),

    /**
     * 特殊原因禁止上新
     */
    BLACK_TEMP("1", "暂停上新"),

    /**
     * 禁止下单与上新
     */
    BLACK_FOREVER("2", "永久拉黑"),

    /**
     * 淘汰供应商
     */
    GREY_LIST("3", "灰名单"),

    /**
     * 重点发展
     */
    GREEN_LIST("4", "绿名单"),

    ;

    private String code;

    private String display;

    private SupplierBlackListType(String code, String display)
    {
        this.code = code;
        this.display = display;
    }

    public static SupplierBlackListType build(String code)
    {
        SupplierBlackListType[] values = values();

        for (SupplierBlackListType type : values)
        {
            if (type.code.equals(code))
            {
                return type;
            }
        }

        return null;
    }

    public String code()
    {
        return code;
    }

    public String display()
    {
        return display;
    }

    public int intCode()
    {
        return Integer.valueOf(this.code).intValue();
    }
}
