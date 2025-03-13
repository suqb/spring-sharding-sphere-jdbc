

package com.suqb.www.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.cglib.beans.BeanCopier;

/**
 * 保存一些公共方法
 * 
 * @author Administrator
 * @version Bessky V100R001 2014年11月3日
 * @since Bessky V100R001C00
 */
public class CommonUtils
{
    
    /**
     * 缓存BeanCopier实例对象
     */
    private static final Map<String, BeanCopier> BEAN_COPIER_MAP = new ConcurrentHashMap<>();
    
    private final static String ORDER_LABEL_PATH = "order/label";
    
    /**
     * 自动+1
     */
    private static final AtomicInteger atomicInteger = new AtomicInteger();

    /**
     * 根据时间重新命名文件命
     * 
     * <p>e.q. order.xls 变成 2016080808121212.xls
     * 
     * @return
     * @throws UnsupportedEncodingException
     * @return String
     */
    public static String newFileName(String filename)
    {
        // 扩展名
        int lastDocIndex = filename.lastIndexOf(".");
        String extensionName = filename.substring(lastDocIndex + 1);

        Date currTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        String timeStr = sdf.format(currTime);

        String newFilename = timeStr + "." + extensionName;

        return newFilename;
    }

    /**
     * 根据时间及自定义字符重新命名文件命
     */
    public static String newFileName(String filename, String custom)
    {
        // 扩展名
        int lastDocIndex = filename.lastIndexOf(".");
        String extensionName = filename.substring(lastDocIndex + 1);

        Date currTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        String timeStr = sdf.format(currTime);

        String newFilename = timeStr + custom + "." + extensionName;

        return newFilename;
    }
    
    /**
     * 使用UUID作为文件名重新命名文件命
     */
    public static String uuidFileName(String filename)
    {
        // 扩展名
        int lastDocIndex = filename.lastIndexOf(".");
        String extensionName = filename.substring(lastDocIndex);

        return UUID.randomUUID() + extensionName;
    }

    /**
     * 根据时间在文件名后面添加时间命名的文件名
     * 
     * <p>e.q. order.xls 变成 order.2016080808121212.xls
     * 
     * @return
     * @throws UnsupportedEncodingException
     * @return String
     */
    public static String newFileNameByTime(String filename)
    {
        // 扩展名
        int lastDocIndex = filename.lastIndexOf(".");
        String extensionName = filename.substring(lastDocIndex + 1);

        String prefixName = filename.substring(0, lastDocIndex);

        Date currTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        String timeStr = sdf.format(currTime);

        String newFilename = prefixName + "_" + timeStr + "." + extensionName;

        return newFilename;
    }

    /**
     * 根据时间在文件名后面添加时间命名的文件名 (年月日时分秒  24H制)
     * 
     * <p>e.q. order.xls 变成 order.20180919094002.xls
     * 
     * @return
     * @throws UnsupportedEncodingException
     * @return String
     */
    public static String newFileNameByTimeAsYmdhms(String filename)
    {
        // 扩展名
        int lastDocIndex = filename.lastIndexOf(".");
        String extensionName = filename.substring(lastDocIndex + 1);

        String prefixName = filename.substring(0, lastDocIndex);

        Date currTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeStr = sdf.format(currTime);

        String newFilename = prefixName + "_" + timeStr + "." + extensionName;

        return newFilename;
    }

    /**
     * 判断集合是否判断
     * 
     * 
     * @param collection
     * @param value
     * @return
     * @return boolean
     */
    public static Boolean contains(Collection<?> collection, String value)
    {
        if (CollectionUtils.isNotEmpty(collection) && value != null)
        {
            return collection.contains(value);
        }

        return false;
    }

    /**
     * 判断数组是否包含
     * 
     * @author 江志超
     * @param array
     * @param objectToFind
     * @return
     */
    public static boolean contains(Object[] array, Object objectToFind)
    {
        for (Object object : array)
        {
            if (object.equals(objectToFind))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * 保存两位小数
     * 
     * 
     * @param input
     * @return
     * @return double
     */
    public static double scaleTwo(double input)
    {
        return NumberUtils.scaleTwo(input);
    }

    /**
     * 是否 double
     * 
     * 
     * @param number
     * @return
     * @return boolean
     */
    public static boolean isDoubleNumber(String number)
    {
        return NumberUtils.isDoubleNumber(number);
    }

    /**
     * 是否int属性
     * 
     * 
     * @param number
     * @return
     * @return boolean
     */
    public static boolean isNumber(String number)
    {
        return NumberUtils.isNumber(number);
    }

    /**
     * html转码
     * 
     * 
     * @param input
     * @return
     * @return String
     */
    public static String html(String input)
    {
        String encode = input;
        try
        {
            encode = URLEncoder.encode(input, "utf-8");
        }
        catch (UnsupportedEncodingException e)
        {
        }

        return encode;
    }

    /**
     * 格式化请求参数
     * <pre> 
     * e.q. url=q=1&q=2&s=3 , paramNames=[q], formatQueryString() = "q=1&q=2"
     * </pre>
     * @return
     */
    public static String formatQueryString(String[][] nameValues)
    {
        StringBuffer queryString = new StringBuffer();

        for (int i = 0; i < nameValues.length; i++)
        {
            if (queryString.length() > 0)
            {
                queryString.append('&');
            }

            int tempSize = nameValues[i].length;

            if ((tempSize >= 2) && (nameValues[i][0] != null))
            {
                queryString.append(nameValues[i][0]);
                queryString.append('=');
                if (nameValues[i][1] != null)
                {
                    queryString.append(nameValues[i][1]);
                }
            }
        }

        return queryString.toString();

    }

    /**
     * 拆分为list
     * @param str 要拆分的字符串
     * @param separator 分隔符
     * @return
     */
    public static List<String> splitList(String str, String separator)
    {
        List<String> stringList = new ArrayList<String>();
        if (StringUtils.isNotBlank(str))
        {
            String[] split = StringUtils.split(str, separator);

            for (int i = 0, len = split.length; i < len; i++)
            {
                String trim = StringUtils.trim(split[i]);
                if (StringUtils.isNotBlank(trim) && !stringList.contains(trim))
                {
                    stringList.add(trim);
                }
            }
        }

        return stringList;
    }

    /**
     * 拆分为list
     * @param str 要拆分的字符串
     * @param separator 分隔符
     * @return
     */
    public static List<Integer> splitIntList(String str, String separator)
    {
        List<Integer> intergerList = new ArrayList<Integer>();
        if (StringUtils.isNotBlank(str))
        {
            String[] split = StringUtils.split(str, separator);

            for (int i = 0, len = split.length; i < len; i++)
            {
                if (StringUtils.isNotBlank(split[i]))
                {
                    intergerList.add(Integer.valueOf(StringUtils.trim(split[i])));
                }
            }
        }

        return intergerList;
    }

    /**
     * 拆分为list,排除转换失败的元素
     * @param str 要拆分的字符串
     * @param separator 分隔符
     * @return
     */
    public static List<Integer> splitIntListExcludeEx(String str, String separator)
    {
        List<Integer> intergerList = new ArrayList<>();
        if (StringUtils.isNotBlank(str))
        {
            String[] split = StringUtils.split(str, separator);

            for (String s : split) {
                if (StringUtils.isNotBlank(s)) {
                    Integer value = null;
                    try
                    {
                        value = Integer.valueOf(StringUtils.trim(s));
                    }
                    catch (Exception e)
                    {
                        /*ignore*/
                    }
                    if (value != null) {
                        intergerList.add(value);
                    }
                }
            }
        }

        return intergerList;
    }

    /**
     * 拆分为list
     * @param str 要拆分的字符串
     * @param separator 分隔符
     * @return
     */
    public static List<Long> splitLongList(String str, String separator)
    {
        List<Long> longList = new ArrayList<Long>();
        if (StringUtils.isNotBlank(str))
        {
            String[] split = StringUtils.split(str, separator);

            for (int i = 0, len = split.length; i < len; i++)
            {
                if (StringUtils.isNotBlank(split[i]))
                {
                    longList.add(Long.valueOf(StringUtils.trim(split[i])));
                }
            }
        }

        return longList;
    }

    /**
     * 单个字段逗号分隔模糊and查询
     * <p>e.q. AND ((name LIKE '%abc%') AND (name LIKE '%123%'))
     * @param tableField 表字段如 t_message.content
     * @param value 条件值
     */
    public static String getMultiFuzzyAndByCommaSplit(String tableField, String value)
    {
        if (StringUtils.isBlank(tableField) || StringUtils.isBlank(value))
        {
            return null;
        }

        String[] split = StringUtils.split(value, ",");
        int len = split.length;
        StringBuffer sb = new StringBuffer(len * 32);
        for (int i = 0; i < len; i++)
        {
            String likeValue = null;
            int doxIndex = split[i].indexOf("'");
            if (doxIndex > 0)
            {
                likeValue = split[i].substring(0, doxIndex) + "'" + split[i].substring(doxIndex);
            }
            else
            {
                likeValue = split[i];
            }

            likeValue = StringUtils.trim(likeValue);

            /**
             AND (product.product_feature = '带电'
                OR product.product_feature LIKE '%,带电'
                OR product.product_feature LIKE '带电,%'
                OR product.product_feature LIKE '%,带电,%')
            AND (product.product_feature = '手表'
                OR product.product_feature LIKE '%,手表'
                OR product.product_feature LIKE '手表,%'
                OR product.product_feature LIKE '%,手表,%')
             */
            sb.append(tableField + " = '" + likeValue + "' ");
            sb.append(" OR ");
            sb.append(tableField + " LIKE '%," + likeValue + "' ");
            sb.append(" OR ");
            sb.append(tableField + " LIKE '" + likeValue + ",%' ");
            sb.append(" OR ");
            sb.append(tableField + " LIKE '%," + likeValue + ",%' ");

            if (i != len - 1)
            {
                sb.append(" ) AND (");
            }
        }

        return "AND (" + sb + ")";
    }

    /**
     * 单个字段separator分隔模糊or查询
     * <p>e.q. AND ((name LIKE '%abc%') OR (name LIKE '%123%'))
     * @param tableField 表字段如 t_message.content
     * @param value 条件值
     * @param separator 分隔符
     */
    public static String getMultiFuzzyOrByCommaSplit(String tableField, String value, String separator)
    {
        if (StringUtils.isBlank(tableField) || StringUtils.isBlank(value))
        {
            return null;
        }

        String[] split = StringUtils.split(value, ",");
        int len = split.length;
        StringBuffer sb = new StringBuffer(len * 32);
        for (int i = 0; i < len; i++)
        {
            String likeValue = null;
            int doxIndex = split[i].indexOf("'");
            if (doxIndex > 0)
            {
                likeValue = split[i].substring(0, doxIndex) + "'" + split[i].substring(doxIndex);
            }
            else
            {
                likeValue = split[i];
            }

            likeValue = StringUtils.trim(likeValue);

            /**
             AND (product.product_feature = '带电'
                OR product.product_feature LIKE '%,带电'
                OR product.product_feature LIKE '带电,%'
                OR product.product_feature LIKE '%,带电,%'
                OR product.product_feature = '手表'
                OR product.product_feature LIKE '%,手表'
                OR product.product_feature LIKE '手表,%'
                OR product.product_feature LIKE '%,手表,%')
             */
            sb.append(tableField + " = '" + likeValue + "' ");
            sb.append(" OR ");
            sb.append(tableField + " LIKE '%" + separator + likeValue + "' ");
            sb.append(" OR ");
            sb.append(tableField + " LIKE '" + likeValue + separator + "%' ");
            sb.append(" OR ");
            sb.append(tableField + " LIKE '%" + separator + likeValue + separator + "%' ");

            if (i != len - 1)
            {
                sb.append(" OR ");
            }
        }

        return "AND (" + sb + ")";

    }

    /**
     * 单个字段separator分隔右模糊or查询
     * <p>e.q. AND ((name LIKE 'abc%') OR (name LIKE '123%'))
     * @param tableField 表字段如 t_message.content
     * @param value 条件值
     * @param separator 分隔符
     */
    public static String getMultiRightFuzzyOrByCommaSplit(String tableField, String value, String separator)
    {
        if (StringUtils.isBlank(tableField) || StringUtils.isBlank(value))
        {
            return null;
        }

        String[] split = StringUtils.split(value, ",");
        int len = split.length;
        StringBuffer sb = new StringBuffer(len * 32);
        for (int i = 0; i < len; i++)
        {
            String likeValue = null;
            int doxIndex = split[i].indexOf("'");
            if (doxIndex > 0)
            {
                likeValue = split[i].substring(0, doxIndex) + "'" + split[i].substring(doxIndex);
            }
            else
            {
                likeValue = split[i];
            }

            sb.append(tableField + " LIKE '" + StringUtils.trim(likeValue) + "%' ");

            if (i != len - 1)
            {
                sb.append(" OR ");
            }
        }

        return "AND (" + sb + ")";

    }

    /**
     * 单个字段逗号分隔模糊or查询
     * <p>e.q. AND ((name LIKE '%abc%') OR (name LIKE '%123%'))
     * @param tableField 表字段如 t_message.content
     * @param value 条件值
     */
    public static String getMultiFuzzyOrByCommaSplit(String tableField, String value)
    {
        return getMultiFuzzyOrByCommaSplit(tableField, value, ",");

    }

    /**
     * base64编码
     * 
     * 
     * @param input
     * @return
     * String
     */
    public static String base64(String input)
    {
        try
        {
            String charset = "utf-8";
            byte[] base64 = new Base64().encode(input.getBytes(charset));
            return new String(base64, charset);
        }
        catch (UnsupportedEncodingException e)
        {
        }

        return null;
    }

    /**
     * 格式化信息
     * 
     * 
     * @param appInfo
     * @return
     * String
     */
    public static String formInfo(String[][] appInfo)
    {
        return formInfo(null, appInfo);
    }

    /**
     * 格式化信息
     * 
     * 
     * @param message
     * @param appInfo
     * @return
     * String
     */
    public static String formInfo(String message, String[][] appInfo)
    {

        StringBuffer sb = new StringBuffer(200);

        if (StringUtils.isNotBlank(message))
        {
            sb.append(message);
        }

        String appendInfo = formAppendInfo(appInfo);
        if ((appendInfo != null) && (appendInfo.trim().length() != 0))
        {
            if (StringUtils.isNotBlank(message))
            {
                sb.append(" | ");
            }
            sb.append(appendInfo);
        }

        return sb.toString();
    }

    /**
     * 格式化信息
     * 
     * 
     * @param appInfo
     * @return
     * String
     */
    private static String formAppendInfo(String[][] appInfo)
    {
        StringBuffer sb = new StringBuffer(20);

        String separator = ", ";

        if (null != appInfo)
        {
            for (int i = 0; i < appInfo.length; i++)
            {
                int tempSize = appInfo[i].length;

                if ((tempSize >= 2) && (appInfo[i][0] != null))
                {
                    sb.append(appInfo[i][0]);
                    sb.append('=');
                    if (appInfo[i][1] == null)
                    {
                        sb.append("").append(separator);
                    }
                    else
                    {
                        sb.append(appInfo[i][1]).append(separator);
                    }
                }
                else
                {
                    if ((tempSize != 1) || (appInfo[i][0] == null))
                    {
                        continue;
                    }

                    sb.append(appInfo[i][0]).append(separator);
                }
            }

        }

        String appendInfo = sb.toString();
        if ((null == appendInfo) || (0 == appendInfo.length()))
        {
            return "";
        }

        // 去除最后一个逗号
        appendInfo = appendInfo.substring(0, appendInfo.length() - separator.length());
        return appendInfo;
    }

    /**
     * String转ASCII码
     * 
     * 
     * @param string
     * @return
     * int
     */
    public static long string2ascii(String string)
    {
        StringBuffer result = new StringBuffer();
        char[] chars = string.toCharArray();

        for (int i = 0; i < chars.length; i++)
        {
            // 转为ASCII码
            result.append((int) chars[i]);
        }

        try
        {
            return Long.valueOf(result.toString());
        }
        catch (NumberFormatException e)
        {
        }

        return Long.MAX_VALUE;
    }

    /**
     * 补字符
     * 
     * 
     * @param input 
     * @param targetLength 补多少位
     * @param fillupChar 补哪个字符
     * @return
     * String
     */
    public static String fillupChar(String input, int targetLength, String fillupChar)
    {
        if (StringUtils.isBlank(input))
        {
            return input;
        }

        if (input.length() >= targetLength)
        {
            return input;
        }

        for (int i = input.length(); i < targetLength; i++)
        {
            input = input + fillupChar;
        }

        return input;
    }

    /**
     * 兼容jdk1.7排序方法
     * 
     * <pre>
     * 
     * </pre>
     * 
     * @param list
     * void
     */
    public static <T extends Comparable<? super T>> void sort(List<T> list)
    {
        Object[] a = list.toArray();
        Arrays.sort(a);
        ListIterator<T> i = list.listIterator();
        for (int j = 0; j < a.length; j++)
        {
            i.next();
            i.set((T) a[j]);
        }
    }

    /**
     * 兼容jdk1.7排序方法
     * 
     * <pre>
     *	
     * </pre>
     * 
     * @param list
     * @param c
     * void
     */
    public static <T> void sort(List<T> list, Comparator<? super T> c)
    {
        Object[] a = list.toArray();
        Arrays.sort(a, (Comparator) c);
        ListIterator<T> i = list.listIterator();
        for (int j = 0; j < a.length; j++)
        {
            i.next();
            i.set((T) a[j]);
        }
    }

    /**
     * 模糊敏感信息
     * 
     * 
     * @param input
     * void
     */
    public static String fuzzySensitiveInfo(String input)
    {
        if (StringUtils.isBlank(input))
        {
            return input;
        }

        int inputLength = input.length();

        int middle = inputLength / 2;

        // 中间字符模糊的长度
        int middleFuzzyNumber = 2;

        String fuzzy = "*****";

        // 随机中间符号
        int hashCode = Math.abs(input.hashCode());
        String[] randomChar = new String[]{"$", "#", "%"};
        StringBuffer middleRandomChar = new StringBuffer();
        for (int i = 0; i < randomChar.length; i++)
        {
            middleRandomChar.append(randomChar[hashCode % randomChar.length]);
        }

        // 两次模糊，避免重复
        input = StringUtils.substring(input, 0, middle) + middleRandomChar + StringUtils.substring(input, middle);
        return StringUtils.substring(input, 0, middle - middleFuzzyNumber) + fuzzy + StringUtils.substring(input, middle + middleFuzzyNumber);
    }

    /**
     * 模糊敏感信息
     * 模糊账号第三、四位
     * 
     * @param input
     * void
     */
    public static String fuzzySensitiveInfoForFinance(String input)
    {
        StringBuilder sbInput = null;
        if (StringUtils.isBlank(input) || StringUtils.length(input) < 5)
        {
            return input;
        }
        
        sbInput = new StringBuilder(input);
        
        // 字符模糊起始位
        int startFuzzyNumber = 2;
        // 字符模糊结束位
        int endFuzzyNumber = 4;

        String fuzzy = "*****";

        return sbInput.replace(startFuzzyNumber, endFuzzyNumber, fuzzy).toString();
    }

    /**
     * 通过描述提取链接地址
     * 
     * 
     * @param desc 产品描述
     * @return
     * @return String[]
     */
    public static String[] extractImgSrc(String desc)
    {

        if (StringUtils.isBlank(desc))
        {
            return null;
        }

        // ?=pattern 正向预查，在任何匹配 pattern 的字符串开始处匹配查找字符串。例如，'Windows (?=95|98|NT|2000)' 能匹配 "Windows 2000" 中的 "Windows" ，但不能匹配 "Windows 3.1" 中的 "Windows"。

        // (?<=re)\w+会匹配以re开头的单词的后半部分(除了re以外的部分)
        // (?<=\\smailno=)[^\\s]+(?=\\s)
        // (?<=src=\")[^\"]+(?=\")
        // (?<=src=\")[^\"]+(\\.jpg|jpeg|gif|bmp|bnp|png)(?=\")
        String reg = "(?<=src=\")[^\"]+(\\.jpg|jpeg|gif|bmp|bnp|png)[^\"]*(?=\")";

        Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(desc);

        List<String> imgList = new ArrayList<String>();

        while (matcher.find())
        {
            String group = matcher.group();
            imgList.add(group);
        }

        String[] imgArray = imgList.toArray(new String[imgList.size()]);
        return imgArray;
    }

    /**
     * 
     * 
     * <pre>
     *	获取随机数
     * </pre>
     * 
     * @author 李彬
     * @param n
     * void
     */
    public static String getRandorm(int n)
    {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < n; i++)
        {
            buffer.append(new Random().nextInt(10));
        }
        return buffer.toString();
    }

    /**
     * str是否以prefix开头
     * 
     * <p>只要有一个命中就好</p>
     * @param str
     * @param prefixList
     * @return
     * boolean
     */
    public static boolean startsWith(String str, List<String> prefixList)
    {
        if (str == null || CollectionUtils.isEmpty(prefixList))
        {
            return false;
        }

        for (String prefix : prefixList)
        {
            if (StringUtils.startsWith(str, prefix))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * searchStringList是否包含str
     * 
     * <p>只要有一个命中就好</p>
     * @param searchStringList
     * @param str
     * @return
     * boolean
     */
    public static boolean containsIgnoreCase(List<String> searchStringList, String str)
    {
        if (str == null || CollectionUtils.isEmpty(searchStringList))
        {
            return false;
        }

        for (String searchString : searchStringList)
        {
            if (StringUtils.equalsIgnoreCase(searchString, str))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * searchStringList是否包含str
     * 
     * <p>只要有一个命中就好</p>
     * @param searchString 含有separator分隔符的字符串
     * @param separator
     * @param str
     * @return
     * boolean
     */
    public static boolean containsIgnoreCase(String searchString, String separator, String str)
    {
        if (str == null || searchString == null)
        {
            return false;
        }

        List<String> searchStringList = splitList(searchString, separator);

        for (String splitString : searchStringList)
        {
            if (StringUtils.equalsIgnoreCase(splitString, str))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * 
     * 转换对象
     * 
     * @param text
     * @return
     * JSONObject
     */
    public static JSONObject parseObject(String text)
    {
        if (StringUtils.isBlank(text))
        {
            return null;
        }

        return JSON.parseObject(text);
    }

    /**
     * 
     * 转换数组
     * 
     * @param text
     * @return
     * JSONArray
     */
    public static JSONArray parseArray(String text)
    {
        if (StringUtils.isBlank(text))
        {
            return null;
        }

        return JSON.parseArray(text);
    }

    /**
     * 
     * 转换字符串
     * 
     * @author 刘南村
     * @param object
     * @return
     */
    public static String toJSONString(Object object)
    {
        if (object == null)
        {
            return null;
        }

        return JSON.toJSONString(object);
    }

    /**
     * <p>默认根据，, \t\n\r分隔符分隔出list</p>
     *
     * <pre>
     * tokenizeToStringArray(null)            = null
     * tokenizeToStringArray("www.baidu.com") = [www.baidu.com]
     * tokenizeToStringArray("a,b,c")         = [a, b, b]
     * </pre>
     * @param str  the String to check, may be null
     * @return an list of parsed Strings, null if null String input
     */
    public static List<String> tokenizeToStringList(String str)
    {
        return tokenizeToStringList(str, "，, \t\n\r");
    }

    /**
     * <p>根据分隔符分隔出list</p>
     *
     * <pre>
     * tokenizeToStringArray(null, " \t\n\r\f")     = null
     * tokenizeToStringArray("www.baidu.com", ".b") = [www, aidu, com]
     * tokenizeToStringArray("a,b,c", "，, \t\n\r")  = [a, b, b]
     * </pre>
     * @param str  the String to check, may be null
     * @param delimiters the delimiter characters, assembled as a {@code String}
     * @return an list of parsed Strings, null if null String input
     */
    public static List<String> tokenizeToStringList(String str, String delimiters)
    {
        if (str == null)
        {
            return null;
        }

        StringTokenizer st = new StringTokenizer(str, delimiters);
        List<String> tokens = new ArrayList<String>();
        while (st.hasMoreTokens())
        {
            String token = st.nextToken().trim();
            if (token.length() > 0)
            {
                tokens.add(token);
            }
        }

        return tokens;
    }
    
    /**
     * 将一个List集合拆分成指定长度的List
     *
     * @param list 母list
     * @param len 子list的长度
     * @return resultList 结果List<List>
     */
    @SuppressWarnings("rawtypes")
    public static List<List> getSubList(List list,int len) 
    {
        if (list == null || list.size() == 0 || len < 1)
        {
            return null;
        }
        List<List> resultList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) 
        {
            if ( i % len == 0 ) 
            {
                int count = i/len;
                
                @SuppressWarnings("unchecked")
                List subList = (List) list.stream().limit((count + 1) * len).skip(count * len).collect(Collectors.toList());
                resultList.add(subList);
            }
        }
        return resultList;
    }

    /**
     * 是否包含非英文字符
     * 
     * @author 江志超
     * @param address
     * @return
     */
    public static boolean containsNotEnglishChar(String address)
    {
        if (StringUtils.isNotBlank(address))
        {
            for (int i = 0, len = address.length(); i < len; i++)
            {
                int ascii = (int) address.charAt(i);
                // 非数字、英文字母、英文字符（160=空格）
                if (ascii > 255)
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 是否包含字符串
     * 
     * @param str
     * @param searchChar a,b,c
     * @return
     */
    public static boolean containsAny(String str, String searchChar)
    {
        if (StringUtils.isBlank(str) || StringUtils.isBlank(searchChar))
        {
            return false;
        }

        String[] splitChar = StringUtils.split(searchChar, ",");

        for (int i = 0; i < splitChar.length; i++)
        {
            if (str.contains(splitChar[i]))
            {
                return true;
            }
        }

        return false;
    }
    
    /**
     * 对象克隆
     *
     * @param source
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T clone(Object source, Class<T> targetClass)
    {
        if (source == null)
        {
            return null;
        }

        try
        {
            String key = source.getClass().getName() + targetClass.getName();
            BeanCopier beanCopier = BEAN_COPIER_MAP.get(key);
            if (beanCopier == null)
            {
                beanCopier = BeanCopier.create(source.getClass(), targetClass, false);
                BEAN_COPIER_MAP.put(key, beanCopier);
            }

            T target = targetClass.newInstance();
            beanCopier.copy(source, target, null);

            return target;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 列表克隆
     *
     * @param sourceList
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> List<T> clone(Collection<?> sourceList, Class<T> targetClass)
    {
        if (sourceList == null)
        {
            return null;
        }

        List<T> targetList = new ArrayList<>(sourceList.size());

        for (Object source : sourceList)
        {
            T target = clone(source, targetClass);
            targetList.add(target);
        }

        return targetList;
    }
    
    /**
     * 
     * 是否符合某项正则
     * 
     * @author 周勇
     * @param text
     * @param pattern
     * @return
     */
    public static Boolean match(String text, String pattern)
    {
        boolean flag = false;
        if (StringUtils.isBlank(text) || StringUtils.isBlank(pattern))
        {
            return false;
        }

        try
        {
            flag = Pattern.matches(pattern, text);
        }
        catch (Exception e)
        {
            return false;
        }

        return flag;
    }
    
    public static String getNewFilename(String filename)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateFormat = new String((sdf.format(new Date())));
        // 以时间重新命名文件名
        String newFilename = CommonUtils.newFileName(filename);
        String filePath = "/" + ORDER_LABEL_PATH + "/" + dateFormat + "/" + newFilename;

        return filePath;
    }

    /**
     * 当前字符串是否包含中文
     * @param str
     * @return
     */
    public static boolean isContainChinese(String str)
    {
        return Pattern.compile("[\u4e00-\u9fa5]").matcher(str).find();
    }
    
    /**
     * 根据时间重新命名文件名
     * 
     * 
     * @param filename
     * @return
     * String
     */
    public static String getNewImageFileName(String filename)
    {
        //原子数
        atomicInteger.getAndIncrement();
        int intValue = atomicInteger.get();
        
        String suffix = String.valueOf(intValue);
        if(intValue < 10)
        {
            suffix = "0" + suffix;
        }
        
        if(suffix.length() > 2)
        {
            suffix = suffix.substring(0, 2);
        }
        String uuId = getIdByUUId();
        
        try
        {
            // 防止生成文件名重复
            Thread.sleep(1);
        }
        catch (InterruptedException e)
        {
            
        }
        
        Date currTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("hhmmssSSS");
        String newfilename = sdf.format(currTime) + suffix + uuId;

        // 扩展名
        String extensionName = filename.substring(filename.lastIndexOf(".") + 1);

        return newfilename + "." + extensionName;
    }
    
    /**
     * 
     * 获取随机数
     * 
     * @author zengyong
     * @return
     */
    private static String getIdByUUId()
    {
        int hashCodes = UUID.randomUUID().toString().hashCode();

        //有可能是负数
        if (hashCodes < 0)
        {
            hashCodes = -hashCodes;

        }
        String code = String.valueOf(hashCodes);
        if (code.length() > 8)
        {
            return code.substring(0, 8);
        }
        return code;
    }
}
