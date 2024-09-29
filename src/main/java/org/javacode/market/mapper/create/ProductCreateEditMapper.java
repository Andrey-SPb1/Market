package org.javacode.market.mapper.create;

import lombok.RequiredArgsConstructor;
import org.javacode.market.mapper.Mapper;
import org.javacode.market.model.dto.create.ProductCreateEditDto;
import org.javacode.market.model.entity.Product;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductCreateEditMapper implements Mapper<ProductCreateEditDto, Product> {

    @Override
    public Product map(ProductCreateEditDto product) {
        return Product.builder()
                .name(product.name())
                .description(product.description())
                .price(product.price())
                .quantityInStock(product.quantityInStock())
                .build();
    }

    @Override
    public Product map(ProductCreateEditDto productCreateEditDto, Product product) {
        product.setName(productCreateEditDto.name());
        product.setDescription(productCreateEditDto.description());
        product.setPrice(productCreateEditDto.price());
        product.setQuantityInStock(productCreateEditDto.quantityInStock());
        return product;
    }
}
