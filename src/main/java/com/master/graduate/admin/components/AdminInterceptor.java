package com.master.graduate.admin.components;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 管理员的登陆拦截器
 *
 * @author Master_Joe lutong99
 * @since 3/1/2020 9:14 PM
 */
public class AdminInterceptor implements HandlerInterceptor {

    /**
     * 登陆的拦截器，若没有登陆就返回登陆页面
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object admin = request.getSession().getAttribute("admin");
        if (admin == null) {
            request.setAttribute("message", "请先登陆后在进行操作");
            request.getRequestDispatcher("/admin/").forward(request, response);
            return false;
        }
        return true;
    }
}
