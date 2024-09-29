package org.javacode.market.service.impl;

import lombok.RequiredArgsConstructor;
import org.javacode.market.mapper.create.ProductCreateEditMapper;
import org.javacode.market.mapper.response.ProductResponseMapper;
import org.javacode.market.model.dto.create.ProductCreateEditDto;
import org.javacode.market.model.dto.response.ProductResponseDto;
import org.javacode.market.model.entity.Product;
import org.javacode.market.repository.ProductRepository;
import org.javacode.market.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductResponseMapper productResponseMapper;
    private final ProductCreateEditMapper productCreateEditMapper;

    @Override
    public Optional<ProductResponseDto> findById(Long id) {
        return productRepository.findById(id)
                .map(productResponseMapper::map);
    }

    @Override
    public Page<ProductResponseDto> findAll(Pageable pageable) {
        Page<Product> page = productRepository.findAll(pageable);
        return new PageImpl<>(page.getContent().stream()
                .map(productResponseMapper::map)
                .toList(), pageable, page.getTotalElements());
    }

    @Override
    public ProductResponseDto create(ProductCreateEditDto productDto) {
        return Optional.of(productDto)
                .map(productCreateEditMapper::map)
                .map(productRepository::save)
                .map(productResponseMapper::map)
                .orElseThrow();
    }

    @Override
    public Optional<ProductResponseDto> update(Long id, ProductCreateEditDto productDto) {
        return productRepository.findById(id)
                .map(book -> productCreateEditMapper.map(productDto, book))
                .map(productRepository::saveAndFlush)
                .map(productResponseMapper::map);
    }

    @Override
    public boolean delete(Long id) {
        return productRepository.findById(id)
                .map(entity -> {
                    productRepository.deleteById(id);
                    productRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
