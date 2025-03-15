package com.persons.speax.controller;

import com.persons.speax.dto.AuthRequestDTO;
import com.persons.speax.dto.ResponseTokenDTO;
import com.persons.speax.entity.User;
import com.persons.speax.entity.UserDetailsImpl;
import com.persons.speax.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final TokenService service;

    public AuthenticationController(AuthenticationManager manager, TokenService service) {
        this.manager = manager;
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ResponseTokenDTO> signIn(@RequestBody @Valid AuthRequestDTO request) {
        var authToken = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication authentication = manager.authenticate(authToken);

        String responseToken = service.generateToken((UserDetailsImpl) authentication.getPrincipal());
        return ResponseEntity.ok(new ResponseTokenDTO(responseToken));
    }
}
