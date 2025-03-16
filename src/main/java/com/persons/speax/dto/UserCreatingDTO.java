package com.persons.speax.dto;

import jakarta.validation.constraints.*;

import java.util.Date;

public record UserCreatingDTO(

        @NotBlank
        @Size(min=2, max=128)
        String name,

        @NotBlank
        @Size(min=8, max=128)
        @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
        String email,

        @NotBlank
        @Size(min=3, max=128) // TODO: increase min length and complexity requirements
        //@Pattern(regexp = "\\d{4,6}")
        String password,

        @NotNull
        Date birthdate

) { }
