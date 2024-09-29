package org.javacode.market.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.javacode.market.exception.InvalidJsonException;
import org.javacode.market.exception.ResourceNotFoundException;
import org.javacode.market.model.dto.create.ProductCreateEditDto;
import org.javacode.market.model.dto.response.ProductResponseDto;
import org.javacode.market.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ObjectMapper objectMapper;

    @GetMapping("/{id}")
    public ResponseEntity<String> getProduct(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(objectMapper.writeValueAsString(productService.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"))));
        } catch (JsonProcessingException e) {
            throw new InvalidJsonException(e.getMessage());
        }
    }

    @GetMapping("/all")
    public Page<ProductResponseDto> getAllProducts(Pageable pageable) {
        return productService.findAll(pageable);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestBody String productJson) {
        try {
            ProductCreateEditDto product = objectMapper.readValue(productJson, ProductCreateEditDto.class);
            return ResponseEntity.ok(objectMapper.writeValueAsString(productService.create(product)));
        } catch (JsonProcessingException e) {
            throw new InvalidJsonException(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@PathVariable("id") Long id,
                                     @RequestBody String productJson) {
        try {
            ProductCreateEditDto product = objectMapper.readValue(productJson, ProductCreateEditDto.class);
            return ResponseEntity.ok(objectMapper.writeValueAsString(productService.update(id, product)
                    .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"))));
        } catch (JsonProcessingException e) {
            throw new InvalidJsonException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return productService.delete(id) ? noContent().build() : notFound().build();
    }

}
