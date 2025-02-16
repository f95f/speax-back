package com.persons.speax.dto;

public record UpdateMessageDTO(
        Long chatId,
        String content
) {
}
