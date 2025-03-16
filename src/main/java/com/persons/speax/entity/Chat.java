package com.persons.speax.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.persons.speax.dto.StartChatDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
