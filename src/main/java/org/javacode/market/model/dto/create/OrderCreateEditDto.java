package org.javacode.market.model.dto.create;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.javacode.market.model.entity.Status;

import java.util.Date;
import java.util.List;

public record OrderCreateEditDto(
        @NotNull
        Long customerId,
        List<ProductCreateEditDto> products,
        @NotNull
        Date orderDate,
        @Size(min = 1, max = 100)
        String shippingAddress,
        @NotNull
        Double totalPrice,
        @NotNull
        Status status) {
}
