package com.alibaba.lwp.draw;

import com.alibaba.lwp.draw.data.DatasourceFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DrawFilter implements Filter {

    Dispatch dispatch;

    static Logger logger = LoggerFactory.getLogger("debug");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        dispatch = new Dispatch();
        DatasourceFactory.init();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException,
            ServletException {

        logger.info("DoFilter ");

        boolean go2 = dispatch.dispatch((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse);
        if (go2) filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }
}
