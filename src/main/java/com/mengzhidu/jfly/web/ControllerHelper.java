package com.mengzhidu.jfly.web;

import com.mengzhidu.jfly.annotation.Action;
import com.mengzhidu.jfly.helper.ClassHelper;
import com.mengzhidu.jfly.web.handler.Handler;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by xinguimeng on 17/11/26.
 */
public class ControllerHelper {

    /**
     * 用于存放请求与处理器的映射关系(简称Action Map)
     */
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<>();


    static {
        // 获取所有的Controller类
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();

        if (CollectionUtils.isNotEmpty(controllerClassSet)) {
            // 遍历controller
            for (Class<?> controllerClass: controllerClassSet) {
                // 获取Controller类中定义的方法
                Method[] methods = controllerClass.getDeclaredMethods();

                if (ArrayUtils.isNotEmpty(methods)) {
                    for (Method method : methods) {
                        // 判断当前方法是否有Action注解
                        if (method.isAnnotationPresent(Action.class)) {
                            // 从Action注解中获取URL解析规则
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            // 验证URL映射规则
                            if (mapping.matches("\\w+:/\\w*")) {
                                String[] array = mapping.split(":");
                                if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
                                    // 获取请求方法与请求路径
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    Request request = new Request(requestMethod, requestPath);
                                    Handler handler = new Handler(controllerClass, method);
                                    // 加入到Action Map中
                                    System.out.println("加入的request和handler为:" + request.toString() + " " + handler.toString());
                                    ACTION_MAP.put(request, handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * 获取Handler
     *
     * @param requestMethod
     * @param requestPath
     * @return
     */
    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }
}
