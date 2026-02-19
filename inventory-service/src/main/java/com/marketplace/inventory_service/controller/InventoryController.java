package com.marketplace.inventory_service.controller;

import com.marketplace.inventory_service.dto.InventoryRequestDto;
import com.marketplace.inventory_service.dto.InventoryResponseDto;
import com.marketplace.inventory_service.service.InventoryService;
import com.marketplace.inventory_service.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addInventory(@RequestBody InventoryRequestDto inventoryRequestDto){
        InventoryResponseDto inventory = inventoryService.createInventory(inventoryRequestDto);
        return new ResponseEntity<>(new ApiResponse(true, "Inventory created", inventory), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateInventory(@PathVariable Long id, @RequestBody InventoryRequestDto inventoryRequestDto){
        InventoryResponseDto updatedInventory = inventoryService.updateInventory(id, inventoryRequestDto);
        return new ResponseEntity<>(new ApiResponse(true, "Inventory updated", updatedInventory), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getInventory(@PathVariable Long id){
        InventoryResponseDto inventory = inventoryService.getInventoryById(id);
        return new ResponseEntity<>(new ApiResponse(true, "Inventory found with id "+id, inventory), HttpStatus.OK);
    }

    @GetMapping("/{skuCode}")
    public ResponseEntity<ApiResponse> checkInventory(@PathVariable String skuCode){
        InventoryResponseDto inventoryBySkuCode = inventoryService.checkInventory(skuCode);
        return new ResponseEntity<>(new ApiResponse(true, "Inventory found with sku code: "+skuCode, inventoryBySkuCode), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Long id){
        inventoryService.deleteInventory(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Inventory deleted successfully", null));
    }

}
