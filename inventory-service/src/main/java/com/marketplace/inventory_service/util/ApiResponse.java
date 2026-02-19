package com.marketplace.inventory_service.util;

import com.marketplace.inventory_service.dto.InventoryResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse <T> {

    private Boolean status;
    private String message;
    private T data;

}
