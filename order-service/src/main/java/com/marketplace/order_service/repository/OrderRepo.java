package com.marketplace.order_service.repository;

import com.marketplace.order_service.entity.Order;
import com.marketplace.order_service.util.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderId(String orderId);

    List<Order> findByOrderStatus(Status orderStatus);

    List<Order> findBySkuCode(String skuCode);

}
