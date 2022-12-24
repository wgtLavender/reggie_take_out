package com.queen.filter;

import com.alibaba.fastjson.JSON;
import com.queen.common.R;
import com.queen.common.util.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author
 * @date 2022/12/01/19:16
 */
@WebFilter(filterName = "LoginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String requestURI = httpServletRequest.getRequestURI();

        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/upload",
                "/user/login",
                "/user/sendMsg"
        };
        boolean urlFlg = checkUrl(urls, requestURI);
        if (urlFlg) {
            chain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        //pc端
        Long employee = (Long) httpServletRequest.getSession().getAttribute("employee");
        log.info("线程id:{}" , Thread.currentThread().getId());
        if (employee != null) {
            BaseContext.setThreadLocal(employee);
            chain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        //手机端
        String phone = (String) httpServletRequest.getSession().getAttribute("phone");
        log.info("线程id:{}" , Thread.currentThread().getId());
        if (phone != null) {
            BaseContext.setThreadLocal(Long.valueOf(phone));
            chain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }



        httpServletResponse.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));


    }

    private boolean checkUrl(String[] urls, String url) {
        for (String ur : urls) {
            boolean match = ANT_PATH_MATCHER.match(ur, url);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
