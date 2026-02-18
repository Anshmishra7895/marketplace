package com.marketplace.product_service.service.impl;

import com.marketplace.product_service.dto.ProductDto;
import com.marketplace.product_service.entity.Category;
import com.marketplace.product_service.entity.Product;
import com.marketplace.product_service.mapper.ProductMapper;
import com.marketplace.product_service.repository.ProductRepo;
import com.marketplace.product_service.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepo productRepo, ProductMapper productMapper){
        this.productRepo = productRepo;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDto createProduct(ProductDto dto) {
        Category category = null;
        if(dto.getCategoryId() != null){
            category = new Category();
            category.setId(dto.getCategoryId());
        }
        Product product = productMapper.toEntity(dto, category);
        Product savedProduct = productRepo.save(product);
        return productMapper.toDto(savedProduct);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto dto) {
        Product product = productRepo.findById(id).orElseThrow(() -> new RuntimeException("Product Not Found"));
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setDiscountedPrice(dto.getDiscountedPrice());
        if(dto.getCategoryId() != null){
            Category category = new Category();
            category.setId(dto.getCategoryId());
            product.setCategory(category);
        }
        Product savedProduct = productRepo.save(product);
        return productMapper.toDto(savedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not Found"));
        return productMapper.toDto(product);
    }

    @Override
    public Page<ProductDto> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> productPage = productRepo.findAll(pageable);
//        return (Page<ProductDto>) productPage.stream().map(page -> productMapper.toDto(page)).toList();
        return productPage.map(productMapper::toDto);
    }

    @Override
    public Page<ProductDto> searchProducts(String keyword, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> productPage = productRepo.searchProducts(keyword, pageable);
        return productPage.map(productMapper::toDto);
    }

    @Override
    public Page<ProductDto> filterProducts(Long categoryId, Double minPrice, Double maxPrice, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> productPage = productRepo.advanceFilter(null, categoryId, minPrice, maxPrice, pageable);
        return  productPage.map(productMapper::toDto);
    }

    @Override
    public Page<ProductDto> advanceFilter(String keyword, Long categoryId, Double minPrice, Double maxPrice, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> productPage = productRepo.advanceFilter(keyword, categoryId, minPrice, maxPrice, pageable);
        return productPage.map(productMapper::toDto);
    }
}
