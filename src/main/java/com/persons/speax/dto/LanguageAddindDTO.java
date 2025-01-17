package com.persons.speax.dto;

import jakarta.validation.constraints.NotBlank;

public record LanguageAddindDTO(

        @NotBlank
        String name
) { }
