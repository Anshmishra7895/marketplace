package com.marketplace.order_service.mapper;

import com.marketplace.order_service.dto.OrderRequestDto;
import com.marketplace.order_service.dto.OrderResponseDto;
import com.marketplace.order_service.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class OrderMapper {

    public Order toEntity(OrderRequestDto orderRequestDto){
        return Order.builder()
                .skuCode(orderRequestDto.getSkuCode())
                .quantity(orderRequestDto.getQuantity())
                .price(orderRequestDto.getPrice())
                .build();
    }

    public OrderResponseDto toDto(Order order){
        return OrderResponseDto.builder()
                .orderNumber(order.getOrderNumber())
                .orderStatus(order.getOrderStatus())
                .build();
    }

}
