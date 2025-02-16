package com.persons.speax.controller;


import com.persons.speax.dto.SendMessageDTO;
import com.persons.speax.dto.UpdateMessageDTO;
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
        List<Message> messages = service.listMessagesByChat(chatId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessage(@PathVariable Long id) {
        Message message = service.getMessage(id);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/send-message")
    public ResponseEntity<Message> sendMessage(@RequestBody SendMessageDTO request) {
        Message message = service.sendMessage(request);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable Long id, @RequestBody UpdateMessageDTO request) {
        Message updatedMessage = service.updateMessage(id, request);
        return ResponseEntity.ok(updatedMessage);
    }

    @PutMapping("/translate/{id}")
    public ResponseEntity<Message> translateMessage(@PathVariable Long id, @RequestBody UpdateMessageDTO request) {
        Message translatedMessage = service.translateMessage(id, request);
        return ResponseEntity.ok(translatedMessage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        service.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }

}
