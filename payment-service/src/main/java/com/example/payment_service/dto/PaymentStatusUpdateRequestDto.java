package com.example.payment_service.dto;

import com.example.payment_service.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentStatusUpdateRequestDto {

    @NotNull
    private PaymentStatus paymentStatus;

}
