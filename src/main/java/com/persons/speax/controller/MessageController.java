package com.persons.speax.controller;


import com.persons.speax.service.MessageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }
}
