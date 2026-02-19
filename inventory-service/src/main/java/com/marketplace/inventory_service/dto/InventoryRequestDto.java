package com.marketplace.inventory_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryRequestDto {

    @NotBlank(message = "Sku code must not be blank")
    private String skuCode;

    @Min(value = 0, message = "Quantity must be 0 or greater")
    private Integer quantity;

}
