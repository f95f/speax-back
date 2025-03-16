package com.persons.speax.service;

import com.persons.speax.dto.SendMessageDTO;
import com.persons.speax.dto.UpdateMessageDTO;
import com.persons.speax.entity.Chat;
import com.persons.speax.entity.Message;
import com.persons.speax.entity.User;
import com.persons.speax.repository.MessageRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.antlr.v4.runtime.Token;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository repository;
    private final EntityManager entityManager;
    private final TokenService tokenService;
    private final UserService userService;


    public MessageService(
            MessageRepository repository,
            EntityManager entityManager,
            TokenService tokenService,
            UserService userService
    ) {
        this.tokenService = tokenService;
        this.entityManager = entityManager;
        this.repository = repository;
        this.userService = userService;
    }


    public List<Message> listMessagesByChat(Long chatId) {
        return repository.findByChatId(chatId).orElseThrow(
                () -> new RuntimeException("Chat not found")
        );
    }


    public Message getMessage(Long id) {
        return repository.findById(id).orElseThrow(
            () -> new RuntimeException("Message not found")
        );
    }

    @Transactional
    public Message sendMessage(SendMessageDTO request, String token) {

        Chat chat = entityManager.getReference(Chat.class, request.chatId());
        Long userId = tokenService.parseUserId(token);
        User sender = userService.getUser(userId);

        if(chat == null) {
            throw new RuntimeException("Chat not found with ID: " + request.chatId());
        }

        return repository.save(new Message(request, chat, sender));
    }


    @Transactional
    public Message updateMessage(Long id, UpdateMessageDTO request) {

        Message message = repository.findById(id).orElseThrow(
            () -> new RuntimeException("Message not found")
        );
        message.setContent(request.content());
        return repository.save(message);
    }


    @Transactional
    public Message translateMessage(Long id, UpdateMessageDTO request) {

            Message message = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Message not found")
            );
            message.setTranslatedContent(request.content());
            return repository.save(message);
    }

    @Transactional
    public void deleteMessage(Long id) {
        repository.deleteById(id);
    }
}
