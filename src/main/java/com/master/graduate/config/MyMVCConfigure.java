package com.master.graduate.config;

import com.master.graduate.admin.components.AdminInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

/**
 * 接管与拓展SpringMVC
 *
 * @author Master_Joe lutong99
 * @since 2/18/2020 2:10 PM
 */
@Configuration
public class MyMVCConfigure implements WebMvcConfigurer {

    /**
     * 数据源
     */
    @Autowired
    DataSource dataSource;

    /**
     * 好像暂时没有什么用，添加一些SpringMVC的配置
     */
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {

            /**
             * 设置一些地址映射
             */
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("customer/index");
                registry.addViewController("/index.html").setViewName("customer/index");
                registry.addViewController("/index").setViewName("customer/index");
                registry.addViewController("/blog").setViewName("customer/blog");
            }

            /**
             * 拦截器配置，这里主要是配置一个管理员登陆身份的拦截器
             */
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/admin/**").
                        excludePathPatterns("/admin/", "/admin/login", "/admin/login.html", "/admin/Wopop_files/**");
            }
        };
    }

}
