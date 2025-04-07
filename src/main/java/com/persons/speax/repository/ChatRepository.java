package com.persons.speax.repository;

import com.persons.speax.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("SELECT c FROM Chat c WHERE c.inviter.id = :userId OR c.invitee.id = :userId")
    List<Chat> findAllByUser(@Param("userId") Long userId);

    @Query("SELECT c FROM Chat c WHERE (c.inviter.id = :userId OR c.invitee.id = :userId) AND c.active = true")
    List<Chat> findByUserAndActiveTrue(@Param("userId") Long userId);

}
