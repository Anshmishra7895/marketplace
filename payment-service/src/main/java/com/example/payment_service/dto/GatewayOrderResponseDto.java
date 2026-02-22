package com.example.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GatewayOrderResponseDto {

    private String gatewayOrderId;
    private BigDecimal amount;
    private String currency;
    private String status;
    private String gatewayName;

}
