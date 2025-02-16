package com.persons.speax.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender_id;


    private String content;
    private String translated_content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
