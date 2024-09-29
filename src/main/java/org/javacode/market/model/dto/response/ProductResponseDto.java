package org.javacode.market.model.dto.response;

public record ProductResponseDto(
        String name,
        String description,
        Double price,
        Integer quantityInStock) {
}
