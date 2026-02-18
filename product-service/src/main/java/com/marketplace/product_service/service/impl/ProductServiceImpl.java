package com.marketplace.product_service.service.impl;

import com.marketplace.product_service.dto.ProductDto;
import com.marketplace.product_service.entity.Category;
import com.marketplace.product_service.entity.Product;
import com.marketplace.product_service.mapper.ProductMapper;
import com.marketplace.product_service.repository.ProductRepo;
import com.marketplace.product_service.service.ProductService;
import com.marketplace.product_service.util.AppConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final ProductMapper productMapper;
    private final String uploadDir = System.getProperty("user.dir")+"/uploads/products/";

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

    @Override
    public ProductDto uploadImage(Long productId, MultipartFile file) throws IOException {
        Product product = productRepo.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        if(file.isEmpty()){
            throw new RuntimeException("Image file is empty");
        }
        Long maxSize= AppConstants.MAX_FILE_SIZE;
        if(file.getSize() > maxSize){
            throw new RuntimeException("File size must be less than 2MB");
        }
        List<String> allowedType = List.of("image/jpg", "image/png", "image/jpeg");
        if(!allowedType.contains(file.getContentType())){
            throw new RuntimeException("Only jpeg, jpg, png allowed");
        }
        String originalName = file.getOriginalFilename();
        if(originalName == null || !originalName.contains(".")){
            throw new RuntimeException("Invalid file name");
        }
        String extension = originalName.substring(originalName.lastIndexOf(".")+1).toLowerCase();
        List<String> allowedExtension = List.of("jpg", "png", "jpeg");
        if(!allowedExtension.contains(extension)){
            throw new RuntimeException("Invalid image extension");
        }
        File folder = new File(uploadDir);

        if(!folder.exists()){
            folder.mkdirs();
        }
        String fileName = UUID.randomUUID().toString()+"."+extension;
        Path filePath = Paths.get(uploadDir+fileName);
        Files.write(filePath, file.getBytes());
        String imageUrl = "/products/images/"+fileName;
        product.setImageUrl(imageUrl);
        Product savedProduct = productRepo.save(product);
        return productMapper.toDto(savedProduct);
    }
}
