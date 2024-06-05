package com.wapp.core.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import com.wapp.core.utils.JwtUtils;
import com.wapp.core.utils.ValidationUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class AuthenticationFilter extends OncePerRequestFilter {

	private static final String PUBLIC_TOKEN = System.getenv("PUBLIC_TOKEN");

	@Override
	protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {
		String token = request.getHeader("Authorization");

		if (ValidationUtils.notEmpty(token) && (token.equals(PUBLIC_TOKEN) || JwtUtils.validateToken(token))) {
			filterChain.doFilter(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
		}
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getRequestURI();
		List<String> publicPaths = new ArrayList<>();

		publicPaths.add("/v1/core/users/register");
		publicPaths.add("/v1/core/users/login");
		publicPaths.add("/healthCheck");

		return publicPaths.contains(path);
	}

}
