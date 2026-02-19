package com.marketplace.order_service.dto;

import com.marketplace.order_service.util.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Stack;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDto {

    private String orderNumber;
    private Status orderStatus;

}
