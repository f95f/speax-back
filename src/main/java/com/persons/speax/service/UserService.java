package com.persons.speax.service;

import com.persons.speax.dto.UserCreatingDTO;
import com.persons.speax.dto.UserUpdatingDTO;
import com.persons.speax.entity.Language;
import com.persons.speax.entity.User;
import com.persons.speax.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> listUsers() {
        return repository.findAll();
    }

    public User getUser(Long id) {
        return repository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("User not found")
        );
    }

    @Transactional
    public User createUser(UserCreatingDTO user) {
        User parsedUser = new User(user);

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

}
