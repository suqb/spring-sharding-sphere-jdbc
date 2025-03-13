/**
* Copyright Shenzhen Bessky Technology Co., Ltd.
* bessky_portal 上午11:49:01
* All right reserved.
*
*/

package com.suqb.www.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * TODO 添加类的一句话简单描述。
 * 
 * @author Administrator
 * @version Bessky V100R001 2015年1月3日
 * @since Bessky V100R001C00
 */
public class NumberUtils
{
    private static Map<String, DecimalFormat> cacheDecimalFormat = new HashMap<String, DecimalFormat>();

    /**
     * 保留小数点后面两位
     * 
     * @param input
     * @return
     */
    public static double format(Double input)
    {
        // # 一个数字，不包括 0
        return format(input, "0.##");
    }

    /**
     * 自定义数字格式
     * 
     * @param input
     * @return
     */
    public static String format2Str(Double input)
    {
        return format2Str(input, "0.##");
    }

    /**
     * 自定义数字格式
     * 
     * @param input
     * @param style
     * @return
     */
    public static double format(Double input, String style)
    {
        double res = 0;
        if (input != null && StringUtils.isNotBlank(style))
        {
            // 0 表示如果位数不足则以 0 填充，# 表示只要有可能就把数字拉上这个位置
            DecimalFormat decimalFormat = getDecimalFormat(style);
            res = Double.valueOf(decimalFormat.format(input));
        }
        return res;
    }

    /**
     * 自定义数字格式
     * 
     * @param input
     * @param style
     * @return
     */
    public static String format2Str(Double input, String style)
    {
        String res = null;
        if (input != null && StringUtils.isNotBlank(style))
        {
            DecimalFormat decimalFormat = getDecimalFormat(style);
            res = decimalFormat.format(input);
        }
        return res;
    }

    public static double scaleOne(double input)
    {
        BigDecimal bd = new BigDecimal(input);
        double result = bd.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
    }
    
    /**
     * 保存两位小数
     * 
     * @param input
     * @return
     */
    public static double scaleTwo(double input)
    {
        BigDecimal bd = new BigDecimal(input);
        double result = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
    }

    /**
     * 保存三位小数(四舍五入)
     * 
     * @param input
     * @return
     */
    public static double scaleThree(double input)
    {
        BigDecimal bd = new BigDecimal(input);
        double result = bd.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
    }

    /**
     * 保存4位小数(四舍五入)
     * 
     * @param input
     * @return
     */
    public static double scaleFour(double input)
    {
        BigDecimal bd = new BigDecimal(input);
        double result = bd.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
    }

    /**
     * 保留两位小数，剩下直接进位
     * 
     * @param input
     * @return
     */
    public static double scaleUpTwo(double input)
    {
        BigDecimal bd = new BigDecimal(input);
        double result = bd.setScale(2, BigDecimal.ROUND_UP).doubleValue();
        return result;
    }

    /**
     * 是否 double
     * 
     * @param number
     * @return
     */
    public static boolean isDoubleNumber(String number)
    {
        int pointCount = 0;

        // 是否负数
        if (StringUtils.isNotBlank(number) && "-".equals(number.substring(0, 1)))
        {
            number = number.substring(1);
        }

        if (StringUtils.isBlank(number))
        {
            return false;
        }

        for (int i = 0; i < number.length(); i++)
        {
            // 不是数字，又不是点
            if ((number.charAt(i) < '0' || number.charAt(i) > '9') && number.charAt(i) != '.')
            {
                return false;
            }
            else
            {
                // 只有有一个.
                if (number.charAt(i) == '.')
                {
                    pointCount++;
                }
            }
        }

        if (pointCount > 1)
        {
            return false;
        }
        else if (pointCount == 1 && number.length() == 1)
        {
            return false;
        }

        try
        {
            Double.parseDouble(number);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }

    }

    /**
     * 是否int属性
     * 
     * @param number
     * @return
     */
    public static boolean isNumber(String number)
    {
        if (StringUtils.isBlank(number))
        {
            return false;
        }

        // 当第一个字符为0的时候 在我们系统中肯定认为不是数字
        if (number.length() > 1 && number.charAt(0) == '0')
        {
            return false;
        }

        for (int i = 0; i < number.length(); i++)
        {
            // 不是数字
            if ((number.charAt(i) < '0' || number.charAt(i) > '9'))
            {
                return false;
            }
        }

        try
        {
            Integer.parseInt(number);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    /**
     * 是否为int类型(如:01也会判定为int类型)
     *
     * @param number
     * @return
     */
    public static boolean isNumberType(String number)
    {
        if (StringUtils.isBlank(number))
        {
            return false;
        }

        for (int i = 0; i < number.length(); i++)
        {
            // 不是数字
            if ((number.charAt(i) < '0' || number.charAt(i) > '9'))
            {
                return false;
            }
        }

        try
        {
            Integer.parseInt(number);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    /**
     * 自动填充0前缀来达到规定位数
     *
     * @param number 源字符
     * @param length 要求字符长度
     * @return
     */
    public static String automaticFillZero(String number, int length)
    {
        if (StringUtils.length(number) >= length)
        {
            return number;
        }

        StringBuffer sb = new StringBuffer(number);
        for (int i = 0, count = length - number.length(); i < count; i++)
        {
            sb.insert(0, 0);
        }

        return sb.toString();
    }

    /**
     * 判断double 是否相等
     * 
     * @param a
     * @param b
     * @return
     */
    public static boolean doubleEqual(Double a, Double b)
    {
        if (a == null || b == null)
        {
            return false;
        }

        BigDecimal data1 = new BigDecimal(a);
        BigDecimal data2 = new BigDecimal(b);
        int compareTo = data1.compareTo(data2);

        return compareTo == 0;
    }

    /**
     * 判断double 是否相等 有误差
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean doubleRelativelyEqual(Double a, Double b) {
        // 允许的误差值
        double preErrorKey=1e-6;

        return Math.abs(a-b)<preErrorKey;
    }

    private static DecimalFormat getDecimalFormat(String style)
    {
        DecimalFormat decimalFormat = cacheDecimalFormat.get(style);

        if (decimalFormat == null)
        {
            decimalFormat = new DecimalFormat();
            decimalFormat.applyPattern(style);
            cacheDecimalFormat.put(style, decimalFormat);
        }
        return decimalFormat;
    }

    /**
     * 2个double 比较大小  
     * a > b  1
     * a == b 0
     * a < b -1
     * @param a
     * @param b
     * @return
     */
    public static int doubleCompare(Double a, Double b)
    {
        BigDecimal data1 = new BigDecimal(a);
        BigDecimal data2 = new BigDecimal(b);
        int compareTo = data1.compareTo(data2);

        return compareTo;
    }

    /**
     * 是否包含数字
     * 
     * @param input
     * @return
     */
    public static boolean containsNumber(String input)
    {
        if (input == null || input.length() == 0)
        {
            return false;
        }

        for (int i = 0; i < input.length(); i++)
        {
            // 包含数字
            if ((input.charAt(i) >= '0' && input.charAt(i) <= '9'))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * 用于按指定的倍数向上舍入
     * 
     * <pre>
     *CEILING函数，用于按指定的倍数significance向上舍入（绝对值增大的方向，即是远离零）后最接近number的数字
     * </pre>
     * 
     * @param number 表示要向上舍入的值。
     * @param significance 表示要舍入的倍数。
     * @return
     */
    public static double ceiling(double number, double significance)
    {
        // 最小倍数
        double multiple = Math.ceil(number / significance) * significance;
        return multiple;
    }

    /**
     * 相减
     * 
     * @param a
     * @param b
     * @return
     */
    public static double doubleSubtract(String a, String b)
    {
        BigDecimal data1 = new BigDecimal(a);
        BigDecimal data2 = new BigDecimal(b);
        BigDecimal subtract = data1.subtract(data2);

        return subtract.doubleValue();
    }

    /**
     * 相加
     * 
     * @param v1
     * @param v2
     * @return
     */
    public static double doubleAdd(double v1, double v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 相除
     * 
     * @param v1
     *            被除数
     * @param v2
     *            除数
     * @param scale
     *            表示表示需要精确到小数点以后几位
     * @return
     */
    public static double doubleDivide(double v1, double v2, int scale)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 相除，有判空
     * 
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位
     * @return
     */
    public static double doubleDivideIf(Double v1, Double v2, int scale)
    {
        if (v1 == null || v2 == null)
        {
            return 0.0d;
        }

        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 相乘
     * 
     * @param v1
     * @param v2
     * @return
     */
    public static Double doubleMultiply(Double v1, Double v2)
    {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.multiply(b2).doubleValue());
    }

    /**
     * 去除最大值和最小值后求平均数
     * 
     * @param doubleValueList
     * @return
     */
    public static double trimmean(List<Double> doubleValueList)
    {
        if (CollectionUtils.isEmpty(doubleValueList))
        {
            return 0.0;
        }

        int size = doubleValueList.size();
        if (size > 2)
        {
            // 排序
            Collections.sort(doubleValueList);

            // 去除最大和最小
            doubleValueList.remove(0);
            doubleValueList.remove(doubleValueList.size() - 1);

            double total = 0.0;
            for (Double doubleValue : doubleValueList)
            {
                total = total + doubleValue;
            }

            // 平均
            return total / doubleValueList.size();
        }
        else
        {
            double total = 0.0;
            for (Double doubleValue : doubleValueList)
            {
                total = total + doubleValue;
            }
            return total / size;
        }
    }

    /**
     * String 转 double
     * 
     * @param str
     * @return
     */
    public static double toDouble(String str)
    {
        return toDouble(str, 0.0d);
    }

    /**
     * String 转 double
     * 
     * @param str           要转换的值
     * @param defaultValue  默认值
     * @return
     */
    public static double toDouble(String str, double defaultValue)
    {
        if (str == null)
        {
            return defaultValue;
        }
        try
        {
            return Double.parseDouble(str);
        }
        catch (NumberFormatException nfe)
        {
            return defaultValue;
        }
    }
    
    /**
     * 
     * 转换数字
     * 
     * @param input
     * @return
     * @return Double
     */
    public static Double toNumber(String input)
    {
        if (StringUtils.isBlank(input))
        {
            return null;
        }

        // 删除逗号
        if (StringUtils.contains(input, ","))
        {
            input = input.replaceAll(",", "");
        }

        // 删除货币符号
        if (StringUtils.contains(input, "$"))
        {
            input = input.replaceAll("\\$", "");
        }
        
        if (StringUtils.contains(input, "¥"))
        {
            input = input.replaceAll("\\¥", "");
        }
        
        if (StringUtils.contains(input, "��"))
        {
            input = input.replaceAll("��", "");
        }
        
        if (StringUtils.contains(input, "￥"))
        {
            input = input.replaceAll("￥", "");
        }
        
        if (StringUtils.contains(input, "%"))
        {
            input = input.replaceAll("\\%", "");
        }
        
        if (StringUtils.contains(input, "("))
        {
            input = input.replaceAll("\\(", "");
        }
        
        if (StringUtils.contains(input, ")"))
        {
            input = input.replaceAll("\\)", "");
        }
        
        if (StringUtils.contains(input, "楼"))
        {
            input = input.replaceAll("楼", "");
        }
        
        try
        {
            return Double.valueOf(input);
        }
        catch (Exception e)
        {
            return Double.valueOf(input);
        }
    }

    /**
     * 大于x小于y的随机数
     *
     * @param x
     * @param y
     * @return
     */
    public static double randomDouble(double x, double y)
    {
        return format(x + Math.random() * (y - x));
    }

    /**
     * 如果是小数，保留两位，非小数，保留整数
     * @param number
     */
    public static String getDoubleString(double number) {
        String numberStr;
        if (((int) number * 1000) == (int) (number * 1000)) {
            //如果是一个整数
            numberStr = String.valueOf((int) number);
        } else {
            DecimalFormat df = new DecimalFormat("######0.00");
            numberStr = df.format(number);
        }
        return numberStr;
    }

    /**
     * 判断数字是否为空或者为0
     * 
     * @param number
     * @return
     */
    public static boolean isBlank(Double number)
    {
        if (number == null)
        {
            return true;
        }

        return number == 0.0;
    }

    /**
     * 去掉小数后面的0
     *
     * @param num
     * @return
     */
    public static String convertDoubleToString(double num)
    {
        BigDecimal bd = new BigDecimal(String.valueOf(num));
        return bd.stripTrailingZeros().toPlainString();
    }
    
    /**
     * 将double格式化为指定小数位的String，不足小数位用0补全
     *
     * @param v     需要格式化的数字
     * @param scale 小数点后保留几位
     * @return
     */
    public static String roundByScale(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        if(scale == 0){
            return new DecimalFormat("0").format(v);
        }
        String formatStr = "0.";
        for(int i=0;i<scale;i++){
            formatStr = formatStr + "0";
        }
        return new DecimalFormat(formatStr).format(v);
    }
}
