package com.mohamednasser.sadaqa.filter;

import com.mohamednasser.sadaqa.security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Service
public class JwtValidateTokenFilter extends OncePerRequestFilter {

    JwtService jwtService;

    public JwtValidateTokenFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                Map<String, Object> emailRole = jwtService.extractClamesAsMap(authHeader, "sub", "role");
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        (String) emailRole.get("sub"),
                        null,
                        List.of(new SimpleGrantedAuthority((String) emailRole.get("role")))
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception ignored) {}
        }
        filterChain.doFilter(request, response);
    }
}
