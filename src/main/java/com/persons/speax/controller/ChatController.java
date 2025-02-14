package com.persons.speax.controller;

import com.persons.speax.service.ChatService;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chats")
public class ChatController {

    private ChatService service;

    public ChatController(ChatService service) {
        this.service = service;
    }
}
