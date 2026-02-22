package com.example.payment_service.service;

import com.example.payment_service.dto.GatewayOrderResponseDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface PaymentGatewayService {

    GatewayOrderResponseDto createOrder(String orderId, BigDecimal amount);

}
