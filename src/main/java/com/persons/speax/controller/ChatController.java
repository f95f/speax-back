package com.persons.speax.controller;

import com.persons.speax.dto.StartChatDTO;
import com.persons.speax.entity.Chat;
import com.persons.speax.service.ChatService;
import org.apache.coyote.Response;
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

    @GetMapping("/list-by-user")
    public ResponseEntity<List<Chat>> listChatsByUser(
            @RequestParam(defaultValue = "false") boolean showActiveOnly
    ) {
        List<Chat> chats = service.listChatsByUser(showActiveOnly);
        return ResponseEntity.ok(chats);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chat> getChat(@PathVariable Long id) {
        Chat chat = service.getChat(id);
        return ResponseEntity.ok(chat);
    }

    @PostMapping("/start-chat")
    public ResponseEntity<Chat> startChat(@RequestBody StartChatDTO request) {
        Chat chat = service.startChat(request);
        return ResponseEntity.ok(chat);
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
