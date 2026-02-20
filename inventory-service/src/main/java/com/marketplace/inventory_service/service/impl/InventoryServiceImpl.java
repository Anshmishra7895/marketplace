package com.marketplace.inventory_service.service.impl;

import com.marketplace.inventory_service.dto.InventoryRequestDto;
import com.marketplace.inventory_service.dto.InventoryResponseDto;
import com.marketplace.inventory_service.entity.Inventory;
import com.marketplace.inventory_service.event.OrderPlacedEvent;
import com.marketplace.inventory_service.exception.OutOfStockException;
import com.marketplace.inventory_service.exception.ResourceNotFound;
import com.marketplace.inventory_service.mapper.InventoryMapper;
import com.marketplace.inventory_service.repository.InventoryRepo;
import com.marketplace.inventory_service.service.InventoryService;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepo inventoryRepo;
    private final InventoryMapper inventoryMapper;

    public InventoryServiceImpl(InventoryRepo inventoryRepo, InventoryMapper inventoryMapper){
        this.inventoryRepo = inventoryRepo;
        this.inventoryMapper = inventoryMapper;
    }

    @Override
    public InventoryResponseDto createInventory(InventoryRequestDto inventoryRequestDto) {
        Inventory inventory = inventoryMapper.toEntity(inventoryRequestDto);
        Inventory savedInventory = inventoryRepo.save(inventory);
        return inventoryMapper.toDto(savedInventory);
    }

    @Override
    public InventoryResponseDto updateInventory(Long id, InventoryRequestDto inventoryRequestDto) {
        Inventory inventory = inventoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Inventory not fond with id: " + id));
        inventory.setSkuCode(inventoryRequestDto.getSkuCode());
        inventory.setQuantity(inventoryRequestDto.getQuantity());
        Inventory savedInventory = inventoryRepo.save(inventory);
        return inventoryMapper.toDto(savedInventory);
    }

    @Override
    public InventoryResponseDto getInventoryById(Long id) {
        Inventory inventory = inventoryRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Inventory not found with id " + id));
        return inventoryMapper.toDto(inventory);
    }

    @Override
    public InventoryResponseDto checkInventory(String skuCode) {
        Inventory inventory = inventoryRepo.findBySkuCode(skuCode).orElseThrow(() -> new ResourceNotFound("Inventory not found sku code: " + skuCode));
        return inventoryMapper.toDto(inventory);
    }

    @Override
    public void deleteInventory(Long id) {
        inventoryRepo.deleteById(id);
    }

    @Override
    public void updateInventory(OrderPlacedEvent orderPlacedEvent) {
        Inventory inventory = inventoryRepo.findBySkuCode(orderPlacedEvent.getSkuCode()).orElseThrow(() -> new ResourceNotFound("Inventory not found"));
        Integer orderedQuantity = orderPlacedEvent.getQuantity();
        Integer availableQuantity = inventory.getQuantity();
        if(orderedQuantity > availableQuantity){
            throw new OutOfStockException("Insufficient stock for this sku code: "+orderPlacedEvent.getSkuCode());
        }
        inventory.setQuantity(availableQuantity - orderedQuantity);
        inventoryRepo.save(inventory);
    }
}
