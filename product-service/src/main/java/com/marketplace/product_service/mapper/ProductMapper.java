package com.marketplace.product_service.mapper;

import com.marketplace.product_service.dto.ProductDto;
import com.marketplace.product_service.entity.Category;
import com.marketplace.product_service.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDto toDto(Product product){
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .discountedPrice(product.getDiscountedPrice())
                .quantity(product.getQuantity())
                .brand(product.getBrand())
                .imageUrl(product.getImageUrl())
                .categoryId(product.getCategory().getId())
                .build();
    }

    public Product toEntity(ProductDto productDto, Category category){
        return Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .discountedPrice(productDto.getDiscountedPrice())
                .brand(productDto.getBrand())
                .imageUrl(productDto.getImageUrl())
                .category(category)
                .build();
    }

}
