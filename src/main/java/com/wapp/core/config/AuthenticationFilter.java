package com.wapp.core.config;

import com.wapp.core.utils.JwtUtils;
import com.wapp.core.utils.MethodsUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final String PUBLIC_TOKEN = System.getenv("PUBLIC_TOKEN");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (MethodsUtils.notEmpty(token) && (token.equals(PUBLIC_TOKEN) || JwtUtils.validateToken(token))) {
            filterChain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.equals("/v1/core/users/register") || path.equals("/healthCheck");
    }

}
