package com.marketplace.order_service.dto.feign;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponseDto {

    private String skuCode;
    private Boolean inStock;
    private Integer availableQuantity;

}
