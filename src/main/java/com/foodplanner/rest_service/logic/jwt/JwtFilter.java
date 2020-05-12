package com.foodplanner.rest_service.logic.jwt;

import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends GenericFilterBean {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public JwtFilter(JwtTokenProvider JwtProvider) {
        this.jwtTokenProvider = JwtProvider;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        String path = ((HttpServletRequest) req).getRequestURI();
        if(!path.startsWith("/auth/")){
            try {
                jwtTokenProvider.validateToken(token);
            } catch (JwtException e) {
                ((HttpServletResponse) res).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
                return;
            }
        }
        filterChain.doFilter(req, res);
    }
}
