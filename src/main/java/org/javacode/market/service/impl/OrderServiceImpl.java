package org.javacode.market.service.impl;

import lombok.RequiredArgsConstructor;
import org.javacode.market.mapper.create.OrderCreateEditMapper;
import org.javacode.market.mapper.response.OrderResponseMapper;
import org.javacode.market.model.dto.create.OrderCreateEditDto;
import org.javacode.market.model.dto.response.OrderResponseDto;
import org.javacode.market.repository.OrderRepository;
import org.javacode.market.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderResponseMapper orderResponseMapper;
    private final OrderCreateEditMapper orderCreateEditMapper;

    @Override
    public Optional<OrderResponseDto> findById(Long id) {
        return orderRepository.findById(id)
                .map(orderResponseMapper::map);
    }

    @Override
    public OrderResponseDto create(OrderCreateEditDto orderDto) {
        return Optional.of(orderDto)
                .map(orderCreateEditMapper::map)
                .map(orderRepository::save)
                .map(orderResponseMapper::map)
                .orElseThrow();
    }
}
