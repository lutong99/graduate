package com.master.graduate.customer.components;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器，没用上，已经在每一个业务逻辑那个地方进行限制了
 *
 * @author Master_Joe lutong99
 * @since 2/18/2020 3:06 PM
 */
@Deprecated
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 登陆拦截器
     */
    @Override
    @Deprecated
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object loginUser = request.getSession().getAttribute("loginUser");
        if (loginUser == null) {
            request.setAttribute("message", "您还没有登陆, 请登录");
            return false;
        } else {
            return true;
        }
    }
}
