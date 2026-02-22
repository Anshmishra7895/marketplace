package com.example.payment_service.dto;

import com.example.payment_service.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponseDto {

    private Long paymentId;
    private String orderId;
    private BigDecimal amount;
    private String transactionId;
    private PaymentStatus paymentStatus;

}
