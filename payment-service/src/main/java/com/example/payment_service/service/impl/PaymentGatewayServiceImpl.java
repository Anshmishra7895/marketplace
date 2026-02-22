package com.example.payment_service.service.impl;

import com.example.payment_service.config.RazorpayConfig;
import com.example.payment_service.dto.GatewayOrderResponseDto;
import com.example.payment_service.service.PaymentGatewayService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentGatewayServiceImpl implements PaymentGatewayService {

    public final RazorpayClient razorpayClient;

    public PaymentGatewayServiceImpl(RazorpayClient razorpayClient){
        this.razorpayClient = razorpayClient;
    }

    @Override
    public GatewayOrderResponseDto createOrder(String orderId, BigDecimal amount) {
        try{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("amount", amount.multiply(BigDecimal.valueOf(100)));
        jsonObject.put("currency", "INR");
        jsonObject.put("receipt", orderId);
            Order order = razorpayClient.orders.create(jsonObject);
            return new GatewayOrderResponseDto(
                    order.get("id"), amount, order.get("currency"), order.get("status"), "RAZORPAY"
            );
        } catch (RazorpayException e) {
            throw new RuntimeException(e);
        }
    }
}
