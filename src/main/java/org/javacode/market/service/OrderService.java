package org.javacode.market.service;

import org.javacode.market.model.dto.create.OrderCreateEditDto;
import org.javacode.market.model.dto.response.OrderResponseDto;

import java.util.Optional;

public interface OrderService {
    Optional<OrderResponseDto> findById(Long id);

    OrderResponseDto create(OrderCreateEditDto orderDto);
}
