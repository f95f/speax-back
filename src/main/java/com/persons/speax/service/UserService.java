package com.persons.speax.service;

import com.persons.speax.dto.UserCreatingDTO;
import com.persons.speax.entity.User;
import com.persons.speax.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public User createUser(UserCreatingDTO user) {
        User parsedUser = new User(user);

        return repository.save(parsedUser);
    }

    @Transactional
    public void updateUser() {
        // update user
    }

    @Transactional
    public void deleteUser() {
        // delete user
    }

    public void getUser() {
        // get user
    }

    public List<User> listUsers() {
        return repository.findAll();
    }
}
