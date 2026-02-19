package com.marketplace.order_service.service.impl;

import com.marketplace.order_service.dto.OrderRequestDto;
import com.marketplace.order_service.dto.OrderResponseDto;
import com.marketplace.order_service.entity.Order;
import com.marketplace.order_service.exception.OrderNotFoundException;
import com.marketplace.order_service.mapper.OrderMapper;
import com.marketplace.order_service.repository.OrderRepo;
import com.marketplace.order_service.service.OrderService;
import com.marketplace.order_service.util.Status;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepo orderRepo, OrderMapper orderMapper){
        this.orderRepo = orderRepo;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) {
        Order order = orderMapper.toEntity(orderRequestDto);
        order.setOrderStatus(Status.NEW);
        order.setOrderNumber(UUID.randomUUID().toString());
        Order savedOrder = orderRepo.save(order);
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
