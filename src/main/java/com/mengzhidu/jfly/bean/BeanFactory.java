package com.mengzhidu.jfly.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Bean工厂
 * <p>
 * 它负责对Bean的一些基本操作，它所实现的功能都是围绕Bean展开的
 * 主要操作包括:
 * 1.Bean的实例化，它负责根据一个类(不是接口)来实例化一个Bean
 * 2.Bean属性的装配，它负责给字段设置值
 *
 *
 */
public class BeanFactory {

    private  static  final Logger LOGGER = LoggerFactory.getLogger(BeanFactory.class);

    /**
     * 完成Bean的实例化
     *
     * @param clazz 要实例化的类
     * @return
     */
    public static Object newInstance(Class<?> clazz) {
        LOGGER.debug("generate instance for {}", clazz.getName());
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            LOGGER.error("generate instance fail {} ", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 调用方法
     *
     * @param object 对象
     * @param method 方法
     * @param args 参数列表
     * @return 返回是结果对象
     */
    public static Object invokeMethod(Object object, Method method, Object ... args) {
        Object result = null;
        try {
            method.setAccessible(true);
            result = method.invoke(object, args);
        } catch (IllegalAccessException|InvocationTargetException e) {
            LOGGER.error("failed to invoke method:{} of object:{}, the args:{}", method, object, args);
            throw  new RuntimeException(e);
        }
        return result;
    }

    /**
     * 设置属性
     * @param object 对象
     * @param field 属性对象
     * @param value 属性值
     */
    public static void setField(Object object, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(object, value);
        } catch (IllegalAccessException e) {
            LOGGER.error("failed to set field {} by value: {} for object {}", field, value, object);
            throw new RuntimeException(e);
        }
    }
}
