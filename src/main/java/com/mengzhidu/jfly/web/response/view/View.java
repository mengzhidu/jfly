package com.mengzhidu.jfly.web.response.view;

/**
 * 视图接口
 * 它可以分为如下几种类型:
 * 1.模板视图，包括jsp、freemarker等用模板来进行展示的
 * 2.数据视图，包括json、xml等纯粹的展示数据内容
 *
 * 用户也可以定义自己的视图，他们只需要知道如何去渲染这个视图即可
 * 不过最简单的还是对jsp文件进行渲染
 * Created by xinguimeng on 17/11/27.
 */
public interface View {
    void render();
}
