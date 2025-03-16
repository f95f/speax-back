package com.persons.speax.entity;

import com.persons.speax.dto.SendMessageDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private String translatedContent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Message(SendMessageDTO request, Chat chat, User sender) {
        this.sender_id = sender;
        this.content = request.content();
        this.chat = chat;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
