package com.mengzhidu.jfly.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射相关的操作
 * 这里的职责相对比较多，它包含了创建实例、调用方法、设置属性等很多功能
 * Created by xinguimeng on 17/11/22.
 */
public class ReflectUtil {

    private  static  final Logger LOGGER = LoggerFactory.getLogger(ReflectUtil.class);

    public static Object newInstance(Class<?> clazz) {
        Object instance;
        try {
            instance = clazz.newInstance();
        } catch (Exception e) {
            LOGGER.error("new instance fail {} ", e);
            throw new RuntimeException(e);
        }
        return instance;
    }

    public static Object invokeMethod(Object object, Method method, Object ... args) {
        Object result = null;
        try {
            method.setAccessible(true);
            result = method.invoke(object, args);
        } catch (IllegalAccessException|InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void setField(Object object, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(object, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
