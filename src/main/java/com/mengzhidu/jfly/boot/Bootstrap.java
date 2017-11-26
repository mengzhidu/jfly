package com.mengzhidu.jfly.boot;

import com.mengzhidu.jfly.bean.BeanContainer;
import com.mengzhidu.jfly.bean.BeanWired;
import com.mengzhidu.jfly.helper.ClassHelper;
import com.mengzhidu.jfly.util.ClassUtil;
import com.mengzhidu.jfly.web.ControllerHelper;

/**
 * 引导类，它是比较特殊的引导类
 * 它是整体的引导类，它负责完成类的装配，就是帮我们完成类的依赖注入等功能
 * 它并不区分controller、service等概念，这些都是web
 *
 * Created by xinguimeng on 17/11/26.
 */
public class Bootstrap {

    public static void init() {
        Class<?>[] classes = {
                ClassHelper.class,
                BeanContainer.class,
                BeanWired.class,
                ControllerHelper.class
        };

        for (Class<?> clazz: classes) {
            ClassUtil.loadClass(clazz.getName(), false);
        }
    }
}
