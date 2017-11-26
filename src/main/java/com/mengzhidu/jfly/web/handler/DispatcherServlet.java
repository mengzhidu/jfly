package com.mengzhidu.jfly.web.handler;

import com.mengzhidu.jfly.bean.BeanContainer;
import com.mengzhidu.jfly.bean.ReflectUtil;
import com.mengzhidu.jfly.boot.Bootstrap;
import com.mengzhidu.jfly.helper.ConfigHelper;
import com.mengzhidu.jfly.util.CodecUtil;
import com.mengzhidu.jfly.web.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 它只是提供一个简单的入口
 * 和很多框架的思想不同，我更倾向于在这里使用注解
 * 然后它只做很薄的一层封装
 * 我们不希望用户修改这个类，这样可以根据WebServlet注解让web.xml中更少的配置
 * 否则的话用户就需要多配置一些东西
 *
 * Created by xinguimeng on 17/11/26.
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet{

    private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        LOGGER.info("dispatcher init 开始...");
        System.out.println("init 开始...");
        // 初始化相关的Helper类
        Bootstrap.init();
        // 获取ServletContext对象，用于注册Servlet
        ServletContext servletContext = config.getServletContext();
        // 注册处理JSP的Servlet
        //ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        //jspServlet.addMapping(ConfigHelper.getJspPath() + "*");
        // 注册处理静态资源的默认Servlet
        //ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        //defaultServlet.addMapping(ConfigHelper.getAppBasePackage() + "*");
//        super.init(config);
        LOGGER.info("dispatcher init 结束...");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("dispatcher service 开始...");
//        super.service(req, resp);
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();

        // 获取Action处理器
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if (handler != null) {
            // 获取Controller类及其Bean实例
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanContainer.getBean(controllerClass);
            // 创建请求参数对象
            Map<String, Object> paramMap = new HashMap<>();
            Enumeration<String> paramNames = req.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                String paramValue = req.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }

            String body = CodecUtil.decodeUrl(StreamUtil.getString(req.getInputStream()));
            if (StringUtils.isNotEmpty(body)) {
                String[] params = body.split("&");
                if (ArrayUtils.isNotEmpty(params)) {
                    for (String param: params) {
                        String[] array = param.split("=");
                        if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
                            String paramName = array[0];
                            String paramValue = array[1];
                            paramMap.put(paramName, paramValue);
                        }
                    }
                }
            }

            Param param = new Param(paramMap);
            // 调用Action方法
            Method actionMethod = handler.getActionMethod();
            Object result = ReflectUtil.invokeMethod(controllerBean, actionMethod, param);
            // 处理Action方法返回值
            if (result instanceof View) {
                // 返回JSP页面
                View view = (View) result;
                String path = view.getPath();
                if (StringUtils.isNotEmpty(path)) {
                    if (path.startsWith("/")) {
                        resp.sendRedirect(req.getContextPath() + path);
                    } else {
                        Map<String, Object> model = view.getModel();
                        for (Map.Entry<String, Object> entry : model.entrySet()) {
                            req.setAttribute(entry.getKey(), entry.getValue());
                        }
                        req.getRequestDispatcher(ConfigHelper.getJspPath() + path).forward(req, resp);
                    }
                }
            } else if (result instanceof Data) {
                // 返回JSON数据
                Data data = (Data) result;
                Object model = data.getModel();
                if (null != model) {
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    PrintWriter writer = resp.getWriter();
                    String json = JsonUtil.toJson(model);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }
        }

        LOGGER.info("dispatcher service 结束...");
    }
}
