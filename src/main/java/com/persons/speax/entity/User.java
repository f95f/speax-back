package com.persons.speax.entity;

import com.persons.speax.dto.UserCreatingDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Date birthDate;

    @Column(updatable = false)
    private Date createdAt;

    private Date updatedAt;

    @Column(columnDefinition = "boolean default false")
    private boolean active;


    public User(UserCreatingDTO userCreatingDTO) {
        this.name = userCreatingDTO.name();
        this.email = userCreatingDTO.email();
        this.password = userCreatingDTO.password();
        this.birthDate = userCreatingDTO.birthdate();
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.active = false;
    }


    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}
