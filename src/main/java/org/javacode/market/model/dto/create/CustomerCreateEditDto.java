package org.javacode.market.model.dto.create;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerCreateEditDto(
        @Size(min = 1, max = 50)
        String firstname,
        @Size(min = 1, max = 50)
        String lastname,
        @Email
        String email,
        @NotBlank
        String contactNumber) {
}
