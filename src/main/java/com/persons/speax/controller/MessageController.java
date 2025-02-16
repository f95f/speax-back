package com.persons.speax.controller;


import com.persons.speax.entity.Message;
import com.persons.speax.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }

    @GetMapping("/list-by-chat/{chatId}")
    public ResponseEntity<List<Message>> listMessagesByChat(@PathVariable Long chatId) {
        return service.listMessagesByChat(chatId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessage(@PathVariable Long id) {
        return service.getMessage(id);
    }

    @PostMapping("/send-message")
    public ResponseEntity<Message> sendMessage(@RequestBody SendMessageDTO request) {
        return service.sendMessage(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable Long id, @RequestBody UpdateMessageDTO request) {
        return service.updateMessage(id, request);
    }

    @PutMapping("/translate/{id}")
    public ResponseEntity<Message> translateMessage(@PathVariable Long id, @RequestBody TranslateMessageDTO request) {
        return service.translateMessage(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        service.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }

}
