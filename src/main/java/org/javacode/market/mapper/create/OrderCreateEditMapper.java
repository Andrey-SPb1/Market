package org.javacode.market.mapper.create;

import lombok.RequiredArgsConstructor;
import org.javacode.market.exception.ResourceNotFoundException;
import org.javacode.market.mapper.Mapper;
import org.javacode.market.model.dto.create.OrderCreateEditDto;
import org.javacode.market.model.entity.Order;
import org.javacode.market.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class OrderCreateEditMapper implements Mapper<OrderCreateEditDto, Order> {

    private final CustomerRepository customerRepository;
    private final ProductCreateEditMapper productCreateEditMapper;

    @Override
    public Order map(OrderCreateEditDto orderDto) {
        return Order.builder()
                .customer(customerRepository.findById(orderDto.customerId())
                        .orElseThrow(() -> new ResourceNotFoundException
                                (String.format("Customer with id %d not found", orderDto.customerId()))))
                .orderDate(new Date())
                .shippingAddress(orderDto.shippingAddress())
                .totalPrice(orderDto.totalPrice())
                .orderStatus(orderDto.status())
                .products(orderDto.products().stream()
                        .map(productCreateEditMapper::map)
                        .toList())
                .build();
    }

    @Override
    public Order map(OrderCreateEditDto orderCreateEditDto, Order order) {
        order.setOrderDate(orderCreateEditDto.orderDate());
        order.setProducts(order.getProducts());
        order.setShippingAddress(orderCreateEditDto.shippingAddress());
        order.setTotalPrice(orderCreateEditDto.totalPrice());
        order.setOrderStatus(orderCreateEditDto.status());
        order.setCustomer(customerRepository.findById(orderCreateEditDto.customerId()).orElseThrow(() -> new ResourceNotFoundException
                (String.format("Customer with id %d not found", orderCreateEditDto.customerId()))));
        return order;
    }
}
