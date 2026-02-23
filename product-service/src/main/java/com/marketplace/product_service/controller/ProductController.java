package com.marketplace.product_service.controller;

import com.marketplace.product_service.dto.CursorPageResponseDto;
import com.marketplace.product_service.dto.ProductDto;
import com.marketplace.product_service.service.ProductService;
import com.marketplace.product_service.util.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
        ProductDto product = productService.createProduct(productDto);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto){
        ProductDto updatedProduct = productService.updateProduct(id, productDto);
        return ResponseEntity.ok(updatedProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id){
        ProductDto productDto = productService.getProductById(id);
        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return new ResponseEntity<>(new ApiResponse(true, "Product Deleted successfully", null), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<ProductDto>> getAllProducts(
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir){
        Page<ProductDto> allProducts = productService.getAllProducts(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @GetMapping("/cursor")
    public ResponseEntity<CursorPageResponseDto> getAllProductsWithCursor(
            @RequestParam(value = "cursor") Long cursor,
            @RequestParam(value = "size") int size){
        CursorPageResponseDto cursorPageResponseDto = productService.getAllProductsWithCursor(cursor, size);
        return ResponseEntity.ok(cursorPageResponseDto);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductDto>> searchProduct(@RequestParam(value = "keyword") String keyword,
                                                          @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        Page<ProductDto> products = productService.searchProducts(keyword, pageNumber, pageSize);
        return ResponseEntity.ok(products);
    }

    @GetMapping("filter")
    public ResponseEntity<Page<ProductDto>> filterProduct(@RequestParam(value = "categoryId", required = false) Long categoryId,
                                                          @RequestParam(value = "minPrice", required = false) Double minPrice,
                                                          @RequestParam(value =  "maxPrice", required = false) Double maxPrice,
                                                          @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        Page<ProductDto> products = productService.filterProducts(categoryId, minPrice, maxPrice, pageNumber, pageSize);
        return ResponseEntity.ok(products);
    }

    @GetMapping("advance-filter")
    public ResponseEntity<Page<ProductDto>> advanceFilter(@RequestParam(value = "keyword", required = false) String keyword,
                                                          @RequestParam(value = "categoryId", required = false) Long categoryId,
                                                          @RequestParam(value = "minPrice", required = false) Double minPrice,
                                                          @RequestParam(value =  "maxPrice", required = false) Double maxPrice,
                                                          @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                          @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
                                                          @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir){
        Page<ProductDto> productDtos = productService.advanceFilter(keyword, categoryId, minPrice, maxPrice, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<ApiResponse> uploadImage(@PathVariable Long id, @RequestBody MultipartFile file) throws IOException {
        ProductDto productDto = productService.uploadImage(id, file);
        return new ResponseEntity<>(new ApiResponse<>(true, "Image uploaded successfully", null), HttpStatus.OK);
    }


}
