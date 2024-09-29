package org.javacode.market.service;

import org.javacode.market.model.dto.create.ProductCreateEditDto;
import org.javacode.market.model.dto.response.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {

    Optional<ProductResponseDto> findById(Long id);

    Page<ProductResponseDto> findAll(Pageable pageable);

    ProductResponseDto create(ProductCreateEditDto user);

    Optional<ProductResponseDto> update(Long id, ProductCreateEditDto user);

    boolean delete(Long id);
}
