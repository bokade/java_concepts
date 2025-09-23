package com.example.javaIO.service;

import com.example.javaIO.model.JwtSecretEntity;
import com.example.javaIO.repository.JwtSecretRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtSecretRepository secretRepository;
    // this commented code is for role based check in annotation preauthorized to set user in authentication object so user role can be checked
/*
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        try {
            JwtSecretEntity secretEntity = secretRepository.findById("default")
                    .orElseThrow(() -> new RuntimeException("Secret not found"));
            byte[] keyBytes = secretEntity.getSecret().getBytes(StandardCharsets.UTF_8);
            SecretKey key = Keys.hmacShaKeyFor(keyBytes);

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            String role = claims.get("role", String.class);

            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid JWT");
            return;
        }

        filterChain.doFilter(request, response);
    }*/

    // below code is for role based check in configiration filter
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

//        String requestPath = request.getRequestURI();  // e.g., /io/chat-secure-jwt-with-header-token
//        String contextPath = request.getContextPath(); // e.g., /io
//        String pathWithoutContext = requestPath.substring(contextPath.length()); // /chat-secure-jwt-with-header-token

        String token = authHeader.substring(7);

        try {
            JwtSecretEntity secretEntity = secretRepository.findById("default")
                    .orElseThrow(() -> new RuntimeException("Secret not found"));

            byte[] keyBytes = secretEntity.getSecret().getBytes(StandardCharsets.UTF_8);
            SecretKey key = Keys.hmacShaKeyFor(keyBytes);

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            String role = claims.get("role", String.class);
            System.out.println("role in filter: " + role);

            String requestPath = request.getRequestURI();
            System.out.println("requestPath: " + requestPath);
            String contextPath = request.getContextPath();
            System.out.println("contextPath " + contextPath);
            String pathWithoutContext = requestPath.startsWith(contextPath)
                    ? requestPath.substring(contextPath.length())
                    : requestPath;

            System.out.println("Request URI: " + requestPath + ", Path without context: " + pathWithoutContext);

            // ✅ check using endsWith for simplicity
            //this if block commented is for admin role only can access the endpoint
         /*  if (requestPath.endsWith("/chat-secure-jwt-with-header-token") && !"ADMIN".equals(role)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Access Denied: Admin role required");
                return;
            }*/

            // Set authentication in Spring Security context
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid JWT");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
