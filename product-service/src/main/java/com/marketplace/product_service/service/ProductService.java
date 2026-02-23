package com.marketplace.product_service.service;

import com.marketplace.product_service.dto.CursorPageResponseDto;
import com.marketplace.product_service.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface ProductService {

    ProductDto createProduct(ProductDto dto);

    ProductDto updateProduct(Long id, ProductDto productDto);

    void deleteProduct(Long id);

    ProductDto getProductById(Long id);

    Page<ProductDto> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir);

    CursorPageResponseDto getAllProductsWithCursor(Long cursor, int size);

    Page<ProductDto> searchProducts(String keyword, int pageNumber, int pageSize);

    Page<ProductDto> filterProducts(Long categoryId, Double minPrice, Double maxPrice, int pageNumber, int pageSize);

    Page<ProductDto> advanceFilter(String keyword, Long categoryId, Double minPrice, Double maxPrice, int pageNumber, int pageSize, String sortBy, String sortDir);

    ProductDto uploadImage(Long productId, MultipartFile file) throws IOException;

}
