package com.msurbaNavSecurity.msurbaNavSecurity.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.msurbaNavSecurity.msurbaNavSecurity.Services.ValidatorService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityInterceptor implements HandlerInterceptor {
    @Autowired
    private ValidatorService validatorService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean success = this.validatorService.validationRolePermission(request, request.getRequestURI(),
        request.getMethod());
        System.out.println(success);
        return success;

    }

}
