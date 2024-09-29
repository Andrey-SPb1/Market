package org.javacode.market.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.javacode.market.exception.InvalidJsonException;
import org.javacode.market.exception.ResourceNotFoundException;
import org.javacode.market.model.dto.create.OrderCreateEditDto;
import org.javacode.market.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    @GetMapping("/{id}")
    public ResponseEntity<String> getOrder(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(objectMapper.writeValueAsString(orderService.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Order with id " + id + " not found"))));
        } catch (JsonProcessingException e) {
            throw new InvalidJsonException(e.getMessage());
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> create(@RequestBody String orderJson) {
        try {
            OrderCreateEditDto order = objectMapper.readValue(orderJson, OrderCreateEditDto.class);
            return ResponseEntity.ok(objectMapper.writeValueAsString(orderService.create(order)));
        } catch (JsonProcessingException e) {
            throw new InvalidJsonException(e.getMessage());
        }
    }

}
