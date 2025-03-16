package com.persons.speax.controller;

import com.persons.speax.dto.AuthRequestDTO;
import com.persons.speax.dto.ResponseTokenDTO;
import com.persons.speax.entity.User;
import com.persons.speax.entity.UserDetailsImpl;
import com.persons.speax.service.TokenService;
import com.persons.speax.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sign-in")
public class AuthenticationController {

    private final AuthenticationManager manager;
    private final UserService service;

    public AuthenticationController(AuthenticationManager manager, UserService service) {
        this.manager = manager;
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ResponseTokenDTO> authenticateUser(@RequestBody AuthRequestDTO request) {
        ResponseTokenDTO token = service.authenticate(request);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}