package com.marketplace.inventory_service.repository;

import com.marketplace.inventory_service.entity.ProcessedOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedOrderRepo extends JpaRepository<ProcessedOrder, Long> {

    Boolean existsByOrderId(String orderId);

}
