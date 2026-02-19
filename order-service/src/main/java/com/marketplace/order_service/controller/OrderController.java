package com.marketplace.order_service.controller;

import com.marketplace.order_service.dto.OrderRequestDto;
import com.marketplace.order_service.dto.OrderResponseDto;
import com.marketplace.order_service.service.OrderService;
import com.marketplace.order_service.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> placeOrder(@RequestBody OrderRequestDto orderRequestDto){
        OrderResponseDto placedOrder = orderService.placeOrder(orderRequestDto);
        return new ResponseEntity<>(new ApiResponse(true, "Order placed successfully", placedOrder), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long id){
        OrderResponseDto order = orderService.findById(id);
        return ResponseEntity.ok(new ApiResponse(true, "Order fetched successfully with id: "+id, order));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse> getOrderByOrderId(@PathVariable String orderId){
        OrderResponseDto order = orderService.findByOrderId(orderId);
        return ResponseEntity.ok(new ApiResponse(true, "Order fetch successfully with order id: "+orderId, order))
    }

    @GetMapping("/{skuCode}")
    public ResponseEntity<ApiResponse> getOrderBySkuCode(@PathVariable String skuCode){
        List<OrderResponseDto> orders = orderService.findBySkuCode(skuCode);
        return ResponseEntity.ok(new ApiResponse(true, "Orders fetch successfully with sku code: "+skuCode, orders));
    }

}
