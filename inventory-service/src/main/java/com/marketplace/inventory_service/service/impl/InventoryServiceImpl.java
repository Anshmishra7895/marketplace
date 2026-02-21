package com.marketplace.inventory_service.service.impl;

import com.marketplace.inventory_service.dto.InventoryRequestDto;
import com.marketplace.inventory_service.dto.InventoryResponseDto;
import com.marketplace.inventory_service.entity.Inventory;
import com.marketplace.inventory_service.entity.ProcessedOrder;
import com.marketplace.inventory_service.event.OrderPlacedEvent;
import com.marketplace.inventory_service.exception.OutOfStockException;
import com.marketplace.inventory_service.exception.ResourceNotFound;
import com.marketplace.inventory_service.mapper.InventoryMapper;
import com.marketplace.inventory_service.repository.InventoryRepo;
import com.marketplace.inventory_service.repository.ProcessedOrderRepo;
import com.marketplace.inventory_service.service.InventoryService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepo inventoryRepo;
    private final InventoryMapper inventoryMapper;
    private final ProcessedOrderRepo processedOrderRepo;

    public InventoryServiceImpl(InventoryRepo inventoryRepo, InventoryMapper inventoryMapper, ProcessedOrderRepo processedOrderRepo){
        this.inventoryRepo = inventoryRepo;
        this.inventoryMapper = inventoryMapper;
        this.processedOrderRepo = processedOrderRepo;
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

    /*
    We are using Idempotency here using @Trnasactional and checking orderId before proceeding further
    If orderId not exist then proceed for further business logic and once inventory saved in DB, then
    save proceedOrder entity so that in case of duplicacy we would have orderId in proceedOrder.
    */
    @Transactional
    @Override
    public void updateInventory(OrderPlacedEvent orderPlacedEvent) {
        if(processedOrderRepo.existsByOrderId(orderPlacedEvent.getOrderId())){
            return;
        }
        Inventory inventory = inventoryRepo.findBySkuCode(orderPlacedEvent.getSkuCode()).orElseThrow(() -> new ResourceNotFound("Inventory not found"));
        Integer orderedQuantity = orderPlacedEvent.getQuantity();
        Integer availableQuantity = inventory.getQuantity();
        if(orderedQuantity > availableQuantity){
            throw new OutOfStockException("Insufficient stock for this sku code: "+orderPlacedEvent.getSkuCode());
        }
        inventory.setQuantity(availableQuantity - orderedQuantity);
        inventoryRepo.save(inventory);
        ProcessedOrder processedOrder = new ProcessedOrder();
        processedOrder.setOrderId(orderPlacedEvent.getOrderId());
        processedOrder.setProcessedAt(LocalDateTime.now());
        processedOrderRepo.save(processedOrder);
    }
}
