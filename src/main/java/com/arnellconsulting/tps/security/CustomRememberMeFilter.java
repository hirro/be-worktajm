package com.arnellconsulting.tps.security;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;

public class CustomRememberMeFilter implements Filter {
    private static final String CSRF_TOKEN = "CSRF";

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
        String ctoken = request.getParameter(CSRF_TOKEN);
        HttpServletRequest req = (HttpServletRequest) request;
        if ((authentication == null) && (ctoken != null) && (getRememberMeCookie(req.getCookies()) != null)) {
            req.getSession().setAttribute(CSRF_TOKEN, ctoken);
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
    }

    public void init(FilterConfig config) throws ServletException {
    }

    private Cookie getRememberMeCookie(Cookie[] cookies) {
        if (cookies != null)
            for (Cookie cookie : cookies) {
                if (AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY.equals(cookie.getName())) {
                    return cookie;
                }
            }
        return null;
    }
}
