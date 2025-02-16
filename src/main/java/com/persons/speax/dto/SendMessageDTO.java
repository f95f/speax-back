package com.persons.speax.dto;

import lombok.Data;

public record SendMessageDTO(
        Long chatId,
        String content
) { }
