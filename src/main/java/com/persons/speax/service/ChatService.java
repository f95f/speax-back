package com.persons.speax.service;

import com.persons.speax.dto.StartChatDTO;
import com.persons.speax.entity.Chat;
import com.persons.speax.entity.User;
import com.persons.speax.exception.ApiValidationException;
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
    private final TokenService tokenService;
    private final UserService userService;

    public ChatService(
            ChatRepository repository,
            EntityManager entityManager,
            TokenService tokenService,
            UserService userService) {

        this.entityManager = entityManager;
        this.repository = repository;
        this.tokenService = tokenService;
        this.userService = userService;
    }


    public List<Chat> listChatsByUser(String token, boolean showActiveOnly) {
        Long userId = tokenService.parseUserId(token);

        if(showActiveOnly) {
            return repository.findByUserAndActiveTrue(userId);
        }

        return repository.findAllByUser(userId);
    }


    public Chat getChat(Long id) {
        return repository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Chat not found")
        );
    }


    public void acceptChat(Long chatId, String token) {

        Chat chat = entityManager.getReference(Chat.class, chatId);

        if(chat == null) {
            throw new EntityNotFoundException("Chat not found with ID: " + chatId);
        }

        if(chat.isActive()) {
            throw new ApiValidationException("Chat is already active.");
        }

        Long userId = tokenService.parseUserId(token);
        Long inviteeId = chat.getInvitee().getId();

        if(inviteeId == null) {
            throw new EntityNotFoundException("User not found in chat.");
        }

        if(!userId.equals(inviteeId)) {
            throw new ApiValidationException("Only invited users can activate the chat.");
        }

        toggleActiveStatus(chatId);
    }



    @Transactional
    public Chat startChat(StartChatDTO request) {

        User inviter = entityManager.getReference(User.class, request.inviterId());
        User invitee = entityManager.getReference(User.class, request.inviteeId());

        if(inviter == null || invitee == null) {
            throw new EntityNotFoundException("User not found");
        }

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
