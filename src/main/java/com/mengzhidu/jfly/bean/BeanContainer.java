package com.mengzhidu.jfly.bean;

import com.mengzhidu.jfly.helper.ClassHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Bean容器，它存储了所有的类对象及其实例
 * 这里的类对象可以是接口，也可以是类，甚至也可以是枚举
 *
 * Created by xinguimeng on 17/11/22.
 */
public class BeanContainer {
    private  static  final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> clazz: beanClassSet) {
            Object obj = ReflectUtil.newInstance(clazz);
            BEAN_MAP.put(clazz, obj);
        }
    }

    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    public static  <T> T getBean(Class<T> clazz) {
        if (!BEAN_MAP.containsKey(clazz)) {
            throw  new RuntimeException("can not get bean by class:" + clazz);
        }

        return (T) BEAN_MAP.get(clazz);
    }
}
