package com.marketplace.product_service.controller;

import com.marketplace.product_service.util.ApiResponse;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ImageServerController {

    private final String uploadDir = System.getProperty("user.dir")+"/upload/products";

    @GetMapping("/products/images/{fileName}")
    public ResponseEntity<ApiResponse> getImage(@PathVariable String fileName) throws IOException{
        Path path = Paths.get(uploadDir + fileName);
        Resource resource = new UrlResource(path.toUri());
        if(!resource.exists()){
            return new ResponseEntity<>(new ApiResponse(false, "Image not found", null), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(new ApiResponse(true, "Image found", resource));
    }

}
