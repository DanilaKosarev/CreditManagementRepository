package com.example.CreditManagement.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.CreditManagement.security.JWTUtil;
import com.example.CreditManagement.services.CurrentUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final CurrentUserDetailsService currentUserDetailsService;
    @Autowired
    public JWTFilter(JWTUtil jwtUtil, CurrentUserDetailsService currentUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.currentUserDetailsService = currentUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if(authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")){
            String jwt = authHeader.substring(7);

            if(jwt.isBlank()){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid JWT Token in Bearer Header");
            } else {
                try {
                    String username = jwtUtil.validateTokenAndRetrieveClaim(jwt);

                    UserDetails userDetails = currentUserDetailsService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch(JWTVerificationException e){
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Invalid JWT Token");
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
