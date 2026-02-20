package com.marketplace.order_service.service.impl;

import com.marketplace.order_service.client.InventoryFeignClient;
import com.marketplace.order_service.dto.OrderRequestDto;
import com.marketplace.order_service.dto.OrderResponseDto;
import com.marketplace.order_service.dto.feign.InventoryResponseDto;
import com.marketplace.order_service.entity.Order;
import com.marketplace.order_service.event.OrderPlacedEvent;
import com.marketplace.order_service.exception.OrderNotFoundException;
import com.marketplace.order_service.exception.OutOfStockException;
import com.marketplace.order_service.kafka.OrderEventProducer;
import com.marketplace.order_service.mapper.OrderMapper;
import com.marketplace.order_service.repository.OrderRepo;
import com.marketplace.order_service.service.OrderService;
import com.marketplace.order_service.util.Status;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final OrderMapper orderMapper;
    private final InventoryFeignClient inventoryFeignClient;
    private final OrderEventProducer orderEventProducer;

    public OrderServiceImpl(OrderRepo orderRepo, OrderMapper orderMapper, InventoryFeignClient inventoryFeignClient, OrderEventProducer orderEventProducer){
        this.orderRepo = orderRepo;
        this.orderMapper = orderMapper;
        this.inventoryFeignClient = inventoryFeignClient;
        this.orderEventProducer = orderEventProducer;
    }

    @Override
    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) {
        InventoryResponseDto inventory = inventoryFeignClient.isInStock(orderRequestDto.getSkuCode());
        if(!inventory.getInStock()){
            throw new OutOfStockException("Product with sku code: "+ orderRequestDto.getSkuCode() +" is out of stock");
        }
        Order order = orderMapper.toEntity(orderRequestDto);
        order.setOrderStatus(Status.NEW);
        String orderId = UUID.randomUUID().toString();
        order.setOrderNumber(orderId);
        Order savedOrder = orderRepo.save(order);
        OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
        orderPlacedEvent.setEventId(UUID.randomUUID().toString());
        orderPlacedEvent.setOrderId(orderId);
        orderPlacedEvent.setSkuCode(orderRequestDto.getSkuCode());
        orderPlacedEvent.setQuantity(orderRequestDto.getQuantity());
        orderPlacedEvent.setEventTime(LocalDateTime.now());
        orderEventProducer.sendOrderEvent(orderPlacedEvent);
        return orderMapper.toDto(savedOrder);
    }

    @Override
    public OrderResponseDto findById(Long id) {
        Order order = orderRepo.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderResponseDto> findBySkuCode(String skuCode) {
        List<Order> orders = orderRepo.findBySkuCode(skuCode);
        return orders.stream().map(orderMapper::toDto).toList();
    }

    @Override
    public OrderResponseDto findByOrderId(String orderId) {
        Order order = orderRepo.findByOrderId(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found with order id " + orderId));
        return orderMapper.toDto(order);
    }
}
