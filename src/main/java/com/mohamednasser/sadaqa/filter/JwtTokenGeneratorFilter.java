package com.mohamednasser.sadaqa.filter;

import com.mohamednasser.sadaqa.security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenGeneratorFilter extends OncePerRequestFilter {

    JwtService jwtService;

    public JwtTokenGeneratorFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = authentication.getName();
            String role = authentication.getAuthorities()
                    .stream()
                    .limit(1)
                    .toList()
                    .get(0)
                    .getAuthority();
            Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("role", role);
            String jwtToken = jwtService.generateJwtToken(username, extraClaims);
            response.setHeader("Authorization", jwtToken);
            System.out.println(username + " " + role + " " + jwtToken);
        }
        filterChain.doFilter(request, response);
    }
}
