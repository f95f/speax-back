package com.persons.speax.security;


import com.persons.speax.repository.UserRepository;
import com.persons.speax.service.TokenService;
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
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository repository;

    public SecurityFilter(TokenService tokenService, UserRepository repository) {
        this.tokenService = tokenService;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();
        String JWTToken = retrieveToken(request);

        // Skip token processing for public endpoints
//        if (uri.equals("/sign-in") || uri.equals("/sign-up")) {
//            filterChain.doFilter(request, response);
//            return;
//        }


        if(JWTToken != null) {
            String subject = tokenService.getSubject(JWTToken);
            UserDetails user = repository.findByEmail(subject);

            var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }



    private String retrieveToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if(authHeader != null) {
            return authHeader.replace("Bearer ", "");
        }

        return null;
    }
}