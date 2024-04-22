package com.vendingmachine.config;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication; // Add this import for Authentication
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

@Component
public class SimpleTokenFilter extends OncePerRequestFilter {

	@Value("${auth.token}")  
    private String AUTH_TOKEN;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        String token = request.getHeader("Authorization");

        if (tokenIsRequired(path) && AUTH_TOKEN.equals(token)) {
            // Token validated, authenticate the user
            Authentication auth = new UsernamePasswordAuthenticationToken("user", null, AuthorityUtils.createAuthorityList("ROLE_USER"));
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else if ("/products/add".equals(path)) {
            // Token not validated, deny access
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
    
    private boolean tokenIsRequired(String path) {
        return "/products/add".equals(path) || 
               path.matches("\\/transaction-sessions\\/[0-9]+\\/reset") ||
               path.matches("\\/money-types\\/[0-9]+\\/update-quantity") ||
               path.matches("\\/products\\/[0-9]+\\/update");
    }
}
