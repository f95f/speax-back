package com.persons.speax.controller;

import com.persons.speax.dto.AuthRequestDTO;
import com.persons.speax.dto.ResponseTokenDTO;
import com.persons.speax.entity.User;
import com.persons.speax.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sign-in")
public class AuthenticationController {

//    @Autowired
//    private AuthenticationManager manager;
//
//    @Autowired
//    private TokenService service;
//
    @PostMapping
    public void signIn(@RequestBody @Valid AuthRequestDTO request) {
//    public ResponseEntity<ResponseTokenDTO> signIn(@RequestBody @Valid AuthRequestDTO request) {

//        var authToken = new UsernamePasswordAuthenticationToken(request.login(), request.password());
//        Authentication authentication = manager.authenticate(authToken);
//
//        String responseToken = service.generateToken((User) authentication.getPrincipal());
//        return ResponseEntity.ok(new ResponseTokenDTO(responseToken));
    }
}
