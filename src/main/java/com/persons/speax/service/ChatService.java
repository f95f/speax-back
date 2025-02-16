package com.persons.speax.service;

import com.persons.speax.repository.ChatRepository;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatRepository repository;

    public ChatService(ChatRepository repository) {
        this.repository = repository;
    }
}
