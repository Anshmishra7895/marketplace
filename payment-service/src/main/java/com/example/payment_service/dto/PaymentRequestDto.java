package com.example.payment_service.dto;

import com.example.payment_service.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequestDto {

    @NotNull(message = "Order id must not be null")
    private String orderId;

    @Positive(message = "Amount must be greater or equals to 0")
    private BigDecimal amount;

    @NotNull(message = "Payment method must not be null")
    private PaymentMethod paymentMethod;

}
