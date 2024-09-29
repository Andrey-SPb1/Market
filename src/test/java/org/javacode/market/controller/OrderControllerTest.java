package org.javacode.market.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.javacode.market.model.dto.create.OrderCreateEditDto;
import org.javacode.market.model.entity.Customer;
import org.javacode.market.model.entity.Order;
import org.javacode.market.model.entity.Status;
import org.javacode.market.repository.CustomerRepository;
import org.javacode.market.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@RequiredArgsConstructor
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderControllerTest {

    private final MockMvc mockMvc;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        loadTestData();
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void getOrder() throws Exception {
        mockMvc.perform(get("/api/v1/order/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("status").value("CANCELLED"))
                .andExpect(jsonPath("customer.firstname").value("Ivan"))
                .andExpect(jsonPath("totalPrice").value(20_000.00));
    }

    @Test
    void create() throws Exception {
        OrderCreateEditDto order = new OrderCreateEditDto(1L, new ArrayList<>(), new Date(),
                "test", 100.00, Status.SHIPPED);

        mockMvc.perform(post("/api/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("status").value("SHIPPED"))
                .andExpect(jsonPath("customer.lastname").value("Ivanov"))
                .andExpect(jsonPath("totalPrice").value(100.00));
    }

    private void loadTestData() {
        if (orderRepository.count() != 0) return;

        Customer customer = Customer.builder()
                .contactNumber("812-31-22")
                .firstName("Ivan")
                .lastName("Ivanov")
                .email("ivanov@gmail.com")
                .build();

        Order order1 = Order.builder()
                .orderStatus(Status.CANCELLED)
                .orderDate(new Date(8742172L))
                .customer(customer)
                .totalPrice(20_000.00)
                .shippingAddress("test avenue")
                .build();

        Order order2 = Order.builder()
                .orderStatus(Status.COMPLETED)
                .orderDate(new Date(8742322L))
                .customer(customer)
                .totalPrice(12_000.00)
                .shippingAddress("test2 avenue")
                .build();

        customerRepository.save(customer);
        orderRepository.saveAll(List.of(order1, order2));
    }
}