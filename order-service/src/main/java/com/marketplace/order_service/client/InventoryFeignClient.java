package com.marketplace.order_service.client;

import com.marketplace.order_service.dto.feign.InventoryResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inventory-service")
public interface InventoryFeignClient {

    @GetMapping("/api/inventory/{skuCode}")
    InventoryResponseDto isInStock(@PathVariable String skuCode);

}
