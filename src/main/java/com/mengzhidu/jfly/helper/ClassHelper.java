package com.mengzhidu.jfly.helper;

import com.mengzhidu.jfly.annotation.Controller;
import com.mengzhidu.jfly.annotation.Service;
import com.mengzhidu.jfly.util.ClassUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by xinguimeng on 17/11/22.
 */
public class ClassHelper {
    private static final Set<Class<?>>  CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    public static Set<Class<?>>  getClassSet() {
        return CLASS_SET;
    }

    public static  Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> result = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(Service.class)) {
                result.add(clazz);
            }
        }
        return result;
    }

    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> result = new HashSet<>();
        for (Class<?> clazz: CLASS_SET) {
            if (clazz.isAnnotationPresent(Controller.class)) {
                result.add(clazz);
            }
        }
        System.out.println("所有的controller集合为:" + result);
        return result;
    }

    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> result = new HashSet<>();
        result.addAll(getServiceClassSet());
        result.addAll(getControllerClassSet());
        return result;
    }
}
