package com.example.payment_service.mapper;

import com.example.payment_service.dto.PaymentRequestDto;
import com.example.payment_service.dto.PaymentResponseDto;
import com.example.payment_service.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class PaymentMapper {

    public PaymentResponseDto toDto(Payment payment){
        return PaymentResponseDto.builder()
                .paymentId(payment.getId())
                .paymentStatus(payment.getPaymentStatus())
                .orderId(payment.getOrderId())
                .transactionId(payment.getTransactionId())
                .amount(payment.getAmount())
                .build();
    }

    public Payment toEntity(PaymentRequestDto paymentRequestDto){
        return Payment.builder()
                .orderId(paymentRequestDto.getOrderId())
                .amount(paymentRequestDto.getAmount())
                .paymentMethod(paymentRequestDto.getPaymentMethod())
                .build();
    }

}
