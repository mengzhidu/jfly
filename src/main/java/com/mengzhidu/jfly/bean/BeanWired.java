package com.mengzhidu.jfly.bean;

import com.mengzhidu.jfly.annotation.Inject;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 进行Bean的自动注入
 * 这一步在所有的Bean实例都被实例化之后再进行
 * 比如A依赖B，B也依赖A，这种情况是可以实现注入的
 * 
 * Created by xinguimeng on 17/11/22.
 */
public class BeanWired {

    /**
     * 日志常量
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BeanWired.class);

    public static void init() {
        // 获取所有的Bean类与Bean实例之间的映射关系(简称Bean Map)
        Map<Class<?>, Object> beanMap = BeanContainer.getBeanMap();
        LOGGER.info("beanMap is {}", beanMap);
        if (null != beanMap && beanMap.size() > 0) {
            for (Map.Entry<Class<?>, Object> entry: beanMap.entrySet()) {
                Class<?> beanClass = entry.getKey();
                Object beanInstance = entry.getValue();
                LOGGER.info("start to wired class {}", beanClass);

                // 获取bean类定义的所有成员变量(
                Field[] beanFields = beanClass.getDeclaredFields();
                LOGGER.info("the class {} fields is {}", beanClass, beanFields);
                if (ArrayUtils.isNotEmpty(beanFields)) {
                    for (Field beanField : beanFields) {
                        // 判断当前BeanField是否带有Inject注解
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            LOGGER.info("class {} ready to wired field {}", beanFieldClass, beanFieldInstance);
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
