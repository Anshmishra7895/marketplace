package com.marketplace.order_service.service;

import com.marketplace.order_service.dto.OrderRequestDto;
import com.marketplace.order_service.dto.OrderResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    OrderResponseDto placeOrder(OrderRequestDto orderRequestDto);

    OrderResponseDto findById(Long id);

    List<OrderResponseDto> findBySkuCode(String skuCode);

    OrderResponseDto findByOrderId(String orderId);

}
