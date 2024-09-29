package org.javacode.market.mapper.response;

import lombok.RequiredArgsConstructor;
import org.javacode.market.mapper.Mapper;
import org.javacode.market.model.dto.response.ProductResponseDto;
import org.javacode.market.model.entity.Product;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductResponseMapper implements Mapper<Product, ProductResponseDto> {

    @Override
    public ProductResponseDto map(Product product) {
        return new ProductResponseDto(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantityInStock()
        );
    }
}
