package com.marketplace.inventory_service.service;

import com.marketplace.inventory_service.dto.InventoryRequestDto;
import com.marketplace.inventory_service.dto.InventoryResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryService {

    InventoryResponseDto createInventory(InventoryRequestDto inventoryRequestDto);

    InventoryResponseDto updateInventory(Long id, InventoryRequestDto inventoryRequestDto);

    InventoryResponseDto getInventoryById(Long id);

    InventoryResponseDto getInventoryBySkuCode(String skuCode);

    void deleteInventory(Long id);

}
