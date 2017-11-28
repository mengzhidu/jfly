package com.mengzhidu.jfly.bean;

import com.mengzhidu.jfly.annotation.Inject;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 在IocHelper的静态代码块中实现相关逻辑
 * 能够完成IoC容器的初始化工作
 * 
 * Created by xinguimeng on 17/11/22.
 */
public class BeanWired {

    static {
        // 获取所有的Bean类与Bean实例之间的映射关系(简称Bean Map)
        Map<Class<?>, Object> beanMap = BeanContainer.getBeanMap();
        if (null != beanMap && beanMap.size() > 0) {
            for (Map.Entry<Class<?>, Object> entry: beanMap.entrySet()) {
                Class<?> beanClass = entry.getKey();
                Object beanInstance = entry.getValue();

                // 获取bean类定义的所有成员变量(
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(beanFields)) {
                    for (Field beanField : beanFields) {
                        // 判断当前BeanField是否带有Inject注解
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null) {
                                BeanFactory.setField(beanInstance,beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }

    }
}
