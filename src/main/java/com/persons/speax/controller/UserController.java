package com.persons.speax.controller;

import com.persons.speax.dto.UserCreatingDTO;
import com.persons.speax.entity.Language;
import com.persons.speax.entity.User;
import com.persons.speax.service.UserService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = service.listUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<User> createUser(
            @RequestBody UserCreatingDTO user,
            UriComponentsBuilder uriBuilder
    ) {

        User createdUser = service.createUser(user);
        URI uri = uriBuilder.path("api/v1/users/{id}").buildAndExpand(createdUser.getId()).toUri();

        return ResponseEntity.created(uri).body(createdUser);
    }
}
