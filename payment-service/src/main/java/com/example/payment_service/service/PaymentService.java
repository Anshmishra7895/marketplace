package com.example.payment_service.service;

import com.example.payment_service.dto.PaymentRequestDto;
import com.example.payment_service.dto.PaymentResponseDto;
import com.example.payment_service.dto.PaymentStatusUpdateRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {

    PaymentResponseDto createPayment(PaymentRequestDto paymentRequestDto);

    PaymentResponseDto getPaymentByOrderId(String orderId);

    PaymentResponseDto updatePaymentStatus(Long paymentId, PaymentStatusUpdateRequestDto paymentStatusUpdateRequest);

}
