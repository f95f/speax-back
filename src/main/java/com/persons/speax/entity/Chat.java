package com.persons.speax.entity;

import com.persons.speax.dto.StartChatDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inviter_id")
    private User inviter;

    @ManyToOne
    @JoinColumn(name = "invitee_id")
    private User invitee;

    private LocalDateTime  createdAt;
    private LocalDateTime updatedAt;
    private boolean active;

    public Chat(StartChatDTO request, User inviter, User invitee) {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.inviter = inviter;
        this.invitee = invitee;
        this.active = false;
    }
}
