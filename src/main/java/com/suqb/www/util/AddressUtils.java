package com.suqb.www.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AddressUtils
{
    /**
     * 封装省市信息
     * @throws DocumentException
     */
    public static Map<String, String> extractProvinceCityEntity() throws DocumentException
    {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File("C:\\workspace\\java\\spring-sharding-sphere-jdbc\\src\\main\\resources\\CollectAddress.xml"));

        Map<String, String> regionMap = new HashMap<>();
        Element rootElement = document.getRootElement();
        Iterator<Element> provinceIterator = rootElement.elementIterator("province");

        // 封装省市信息
        while (provinceIterator.hasNext())
        {
            Element province = provinceIterator.next();
            String provinceCode = province.attributeValue("code");
            String provinceName = province.attributeValue("name");
            regionMap.put(provinceCode, provinceName);

            Iterator<Element> cityIterator = province.elementIterator();

            while (cityIterator.hasNext())
            {
                Element city = cityIterator.next();
                String cityCode = city.attributeValue("code");
                String cityName = city.attributeValue("name");
                regionMap.put(cityCode, cityName);
            }
        }

        return regionMap;
    }
}
