package org.javacode.market.model.dto.response;

public record CustomerResponseDto(
        String firstname,
        String lastname,
        String email,
        String contactNumber) {
}
