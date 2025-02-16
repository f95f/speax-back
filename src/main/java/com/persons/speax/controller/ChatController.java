package com.persons.speax.controller;

import com.persons.speax.dto.StartChatDTO;
import com.persons.speax.entity.Chat;
import com.persons.speax.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chats")
public class ChatController {

    private final ChatService service;

    public ChatController(ChatService service) {
        this.service = service;
    }

    @GetMapping("/list-by-user/{userId}")
    public ResponseEntity<List<Chat>> listChatsByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "false") boolean showActiveOnly
    ) {
        return service.listChatsByUser(userId, showActiveOnly);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chat> getChat(@PathVariable Long id) {
        return service.getChat(id);
    }

    @PostMapping("/start-chat")
    public ResponseEntity<Chat> startChat(@RequestBody StartChatDTO request) {
        return service.startChat(request);
    }

    @PutMapping("/toggle-active-status/{id}")
    public ResponseEntity<Void> toggleActiveStatus(@PathVariable Long id) {
       service.toggleActiveStatus(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
        service.deleteChat(id);
        return ResponseEntity.noContent().build();
    }


}
