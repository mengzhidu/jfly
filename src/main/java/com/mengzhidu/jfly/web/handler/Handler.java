package com.mengzhidu.jfly.web.handler;

import java.lang.reflect.Method;

/**
 * 默认的处理器
 * 用户可以定义自己的处理器，从而实现更加复杂的处理机制
 * Created by xinguimeng on 17/11/26.
 */
public class Handler {

    /**
     * Controller类
     */
    private Class<?> controllerClass;

    /**
     * Action方法
     */
    private Method actionMethod;


    public Handler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
