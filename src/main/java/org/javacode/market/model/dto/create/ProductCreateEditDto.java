package org.javacode.market.model.dto.create;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductCreateEditDto(
        @Size(min = 1, max = 50)
        String name,
        @Size(max = 200)
        String description,
        @NotNull
        Double price,
        @NotNull
        Integer quantityInStock) {
}
