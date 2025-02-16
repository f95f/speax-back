package com.persons.speax.service;

import com.persons.speax.dto.StartChatDTO;
import com.persons.speax.entity.Chat;
import com.persons.speax.entity.User;
import com.persons.speax.repository.ChatRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@Service
public class ChatService {

    private final ChatRepository repository;
    private final EntityManager entityManager;

    public ChatService(ChatRepository repository, EntityManager entityManager) {
        this.entityManager = entityManager;
        this.repository = repository;
    }


    public List<Chat> listChatsByUser(boolean showActiveOnly) {
        if(showActiveOnly) {
            return repository.findByActiveTrue();
        }

        return repository.findAll();
    }


    public Chat getChat(Long id) {
        return repository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Chat not found")
        );
    }


    @Transactional
    public Chat startChat(StartChatDTO request) {

        User inviter = entityManager.getReference(User.class, request.inviterId());
        User invitee = entityManager.getReference(User.class, request.inviteeId());

        Chat chat = new Chat(request, inviter, invitee);
        return repository.save(chat);
    }

    @Transactional
    public void toggleActiveStatus(Long id) {
        Chat chat = repository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Chat not found")
        );
        chat.setActive(!chat.isActive());
        repository.save(chat);
    }

    @Transactional
    public void deleteChat(Long id) {
        repository.deleteById(id);
    }
}
