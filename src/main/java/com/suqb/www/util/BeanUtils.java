package com.suqb.www.util;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BeanUtils {
    private static final Map<String, BeanCopier> BEAN_COPIER_MAP = new ConcurrentHashMap();

    public BeanUtils() {
    }

    public static <T> T clone(Object source, Class<T> targetClass) throws Exception
    {
        if (source == null) {
            return null;
        } else {
            try {
                String key = source.getClass().getName() + targetClass.getName();
                BeanCopier beanCopier = (BeanCopier)BEAN_COPIER_MAP.get(key);
                if (beanCopier == null) {
                    beanCopier = BeanCopier.create(source.getClass(), targetClass, false);
                    BEAN_COPIER_MAP.put(key, beanCopier);
                }

                T target = targetClass.newInstance();
                beanCopier.copy(source, target, (Converter)null);
                return target;
            } catch (Exception var5) {
                Exception e = var5;
                throw new Exception(e.getMessage(), e);
            }
        }
    }

    public static <T> List<T> clone(Collection<?> sourceList, Class<T> targetClass) throws Exception
    {
        if (sourceList == null) {
            return null;
        } else {
            List<T> targetList = new ArrayList(sourceList.size());
            Iterator var3 = sourceList.iterator();

            while(var3.hasNext()) {
                Object source = var3.next();
                T target = clone(source, targetClass);
                targetList.add(target);
            }

            return targetList;
        }
    }
}
