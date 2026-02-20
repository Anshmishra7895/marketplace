package com.marketplace.inventory_service.kafka.consumer;

import com.marketplace.inventory_service.event.OrderPlacedEvent;
import com.marketplace.inventory_service.service.InventoryService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {

    private final InventoryService inventoryService;

    public OrderEventConsumer(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    @KafkaListener(topics = "order-event", groupId = "inventory-group")
    public void consume(OrderPlacedEvent orderPlacedEvent){
        inventoryService.updateInventory(orderPlacedEvent);
    }

}
