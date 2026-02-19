package com.marketplace.inventory_service.mapper;

import com.marketplace.inventory_service.dto.InventoryRequestDto;
import com.marketplace.inventory_service.dto.InventoryResponseDto;
import com.marketplace.inventory_service.entity.Inventory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class InventoryMapper {

    public Inventory toEntity(InventoryRequestDto inventoryRequestDto){
        Inventory inventory = new Inventory();
        inventory.setQuantity(inventoryRequestDto.getQuantity());
        inventory.setSkuCode(inventoryRequestDto.getSkuCode());
        return inventory;
    }

    public InventoryResponseDto toDto(Inventory inventory){
        return InventoryResponseDto.builder()
                .skuCode(inventory.getSkuCode())
                .isAvailable(inventory.getQuantity() > 0)
                .availableQuantity(inventory.getQuantity())
                .build();
    }

}
