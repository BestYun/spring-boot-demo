package com.readchen.springbootfilter;

import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

@Log4j2
public class MyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        boolean doFilter = true;
        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) servletResponse);
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        log.info(request.getRequestURI());

        if (request.getRequestURI().startsWith("/users")||request.getRequestURI().startsWith("/error")){

//            符合条件就继续处理
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
//            不符合条件 处理
            log.info("不符合条件 处理"+request.getRequestURI());
            wrapper.sendRedirect("/error");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("在服务启动时初始化");
    }

    @Override
    public void destroy() {
        log.info("销毁");
    }
}
