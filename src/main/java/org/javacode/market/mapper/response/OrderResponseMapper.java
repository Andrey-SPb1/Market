package org.javacode.market.mapper.response;

import lombok.RequiredArgsConstructor;
import org.javacode.market.mapper.Mapper;
import org.javacode.market.model.dto.response.OrderResponseDto;
import org.javacode.market.model.entity.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderResponseMapper implements Mapper<Order, OrderResponseDto> {

    private final CustomerResponseMapper customerResponseMapper;
    private final ProductResponseMapper productResponseMapper;

    @Override
    public OrderResponseDto map(Order order) {
        return new OrderResponseDto(
                customerResponseMapper.map(order.getCustomer()),
                order.getProducts().stream()
                        .map(productResponseMapper::map)
                        .toList(),
                order.getOrderDate(),
                order.getShippingAddress(),
                order.getTotalPrice(),
                order.getOrderStatus()
        );
    }
}
