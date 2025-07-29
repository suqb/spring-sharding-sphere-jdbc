/**
* Copyright Shenzhen Bessky Technology Co., Ltd.
* bessky_portal 下午3:48:15
* All right reserved.
*
*/

package com.suqb.www.constant;

/**
 * 结款方式
 * 
 * @author Administrator
 * @version Bessky V100R001 2015年1月19日
 * @since Bessky V100R001C00
 */
public enum SupplierPaymentMethod
{

    MONTHLY_BALANCE("1", "月结"),
    
    DOUBLE_MONTHLY_BALANCE("11", "双月结"),
    
    WF_PAYMENT("13", "WF支付"),

    CASH_OFFLINE("4", "线上现结"),

    CBT_PAYMENT("9", "跨境宝支付"),

    E_ON_CREDIT("10", "先采后付"),

    PAYMENT_DAYS("5", "阿里账期"),

    HALF_MONTHLY_BALANCE("2", "半月结"),

    WEEK_BALANCE("3", "周结"),

    CASH_INLINE("6", "线下现结"),

    WIRE_TRANSFER("7", "电汇"),

    CASH_ON_DELIVERY("8", "款到发货"),
    
    DELIVERY_AFTER_PAYMENT("12", "货到付款"),
    
    ;

    private String code;

    private String display;

    private SupplierPaymentMethod(String code, String display)
    {
        this.code = code;
        this.display = display;
    }

    /**
     * 根据code构造枚举
     * 
     * <p>
     * TODO 方法功能描述
     * 
     * @param code
     * @return
     * @return EbayCategories
     */
    public static SupplierPaymentMethod build(String code)
    {
        SupplierPaymentMethod[] values = values();

        for (SupplierPaymentMethod type : values)
        {
            if (type.code.equals(code))
            {
                return type;
            }
        }

        return null;
    }
    
    /**
     * 
     * <p>
     * TODO 方法功能描述
     * 
     * @return
     * 
     */
    public String code()
    {
        return code;
    }
    
    public int intCode()
    {
        return Integer.valueOf(this.code).intValue();
    }


    /**
     * 
     * <p>
     * TODO 方法功能描述
     * 
     * @return
     * 
     */
    public String display()
    {
        return display;
    }
}
