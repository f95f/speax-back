package com.persons.speax.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Date;

public record UserUpdatingDTO(
    @NotBlank
    @Size(min=2, max=128)
    String name,

    @NotBlank
    @Size(min=8, max=128)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
    String email,

    @NotNull
    Date birthdate
) { }