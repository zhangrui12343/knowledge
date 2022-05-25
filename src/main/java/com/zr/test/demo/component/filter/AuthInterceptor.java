package com.zr.test.demo.component.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限拦截器
 *
 * @author zr
 */
@Slf4j
@Order(1)
public class AuthInterceptor implements HandlerInterceptor {

     @Override
     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {


         String uri = request.getRequestURI();
        request.getHeader("token");

         return true;
     }

}

