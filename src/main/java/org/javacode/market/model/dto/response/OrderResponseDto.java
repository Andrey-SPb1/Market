package org.javacode.market.model.dto.response;

import org.javacode.market.model.entity.Status;

import java.util.Date;
import java.util.List;

public record OrderResponseDto(
        CustomerResponseDto customer,
        List<ProductResponseDto> products,
        Date orderDate,
        String shippingAddress,
        Double totalPrice,
        Status status) {
}
