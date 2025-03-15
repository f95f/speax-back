package com.persons.speax.security;

import com.persons.speax.entity.User;
import com.persons.speax.entity.UserDetailsImpl;
import com.persons.speax.repository.UserRepository;
import com.persons.speax.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class UserAuthenticationFilter  extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository repository;

    public UserAuthenticationFilter(TokenService tokenService, UserRepository repository) {
        this.tokenService = tokenService;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        if (isUriPublic(request.getRequestURI())) {
            // Skip token validation for public URIs.
            filterChain.doFilter(request, response);
            return;
        }


        String token = retrieveToken(request);

        if(token != null && tokenService.isValid(token)) {
            String subject = tokenService.getSubject(token);
            User user = repository.findByEmail(subject).orElseThrow();
            UserDetailsImpl userDetails = new UserDetailsImpl(user);

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            throw new RuntimeException("Token not found");
        }
        filterChain.doFilter(request, response);

    }


    private String retrieveToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }


    private Boolean isUriPublic(String uri) {
        return Arrays.asList(SecurityConfiguration.PUBLIC_URIS).contains(uri);
    }
}
