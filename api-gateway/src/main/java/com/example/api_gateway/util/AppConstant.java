package com.example.api_gateway.util;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AppConstant {

    public static final List<String> PUBLIC_URLS = List.of("/api/users/register", "/api/users/login");

    public static final Map<String, Map<String, List<String>>> ROLE_API_PERMISSIONS = Map.of(
            "USER", Map.of("GET", List.of("/api/products")),
            "ADMIN", Map.of(
                    "GET", List.of("/api/products"),
                    "POST", List.of("/api/products"),
                    "PUT", List.of("/api/products"),
                    "DELETE", List.of("/api/products")
            )
    );

}
