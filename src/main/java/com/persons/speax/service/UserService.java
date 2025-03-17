package com.persons.speax.service;

import com.persons.speax.dto.AuthRequestDTO;
import com.persons.speax.dto.ResponseTokenDTO;
import com.persons.speax.dto.UserCreatingDTO;
import com.persons.speax.dto.UserUpdatingDTO;
import com.persons.speax.entity.Language;
import com.persons.speax.entity.User;
import com.persons.speax.entity.UserDetailsImpl;
import com.persons.speax.repository.UserRepository;
import com.persons.speax.security.SecurityConfiguration;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository repository,
            AuthenticationManager authenticationManager,
            TokenService tokenService,
            PasswordEncoder passwordEncoder) {

        this.repository = repository;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;

    }

    public List<User> listUsers() {
        return repository.findAll();
    }

    public User getUser(Long id) {
        return repository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("User not found.")
        );
    }

    @Transactional
    public User createUser(UserCreatingDTO user) {
        User parsedUser = new User(user);
        parsedUser.setPassword(passwordEncoder.encode(parsedUser.getPassword()));
        return repository.save(parsedUser);
    }

    @Transactional
    public User updateUser(UserUpdatingDTO user, Long id) {
        User parsedUser = new User(user);

        parsedUser.setId(id);
        return repository.save(parsedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        this.repository.deleteById(id);
    }

    public ResponseTokenDTO authenticate(AuthRequestDTO request) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(request.email(), request.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new ResponseTokenDTO(tokenService.generateToken(userDetails));
    }
}
