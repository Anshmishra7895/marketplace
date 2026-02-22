package com.example.payment_service.service.impl;

import com.example.payment_service.dto.PaymentRequestDto;
import com.example.payment_service.dto.PaymentResponseDto;
import com.example.payment_service.dto.PaymentStatusUpdateRequestDto;
import com.example.payment_service.entity.Payment;
import com.example.payment_service.enums.PaymentStatus;
import com.example.payment_service.exception.PaymentNotFoundException;
import com.example.payment_service.mapper.PaymentMapper;
import com.example.payment_service.repository.PaymentRepo;
import com.example.payment_service.service.PaymentService;
import com.example.payment_service.util.TransactionGenerator;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepo paymentRepo;
    private final PaymentMapper paymentMapper;

    public PaymentServiceImpl(PaymentRepo paymentRepo, PaymentMapper paymentMapper){
        this.paymentRepo = paymentRepo;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public PaymentResponseDto createPayment(PaymentRequestDto paymentRequestDto) {
        Payment payment = paymentMapper.toEntity(paymentRequestDto);
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setTransactionId(TransactionGenerator.generate());
        Payment savedPayment = paymentRepo.save(payment);
        return paymentMapper.toDto(savedPayment);
    }

    @Override
    public PaymentResponseDto getPaymentByOrderId(String orderId) {
        Payment payment = paymentRepo.findByOrderId(orderId).orElseThrow(() -> new PaymentNotFoundException("Payment not found with order id: " + orderId));
        return paymentMapper.toDto(payment);
    }

    @Override
    public PaymentResponseDto updatePaymentStatus(Long paymentId, PaymentStatusUpdateRequestDto paymentStatusUpdateRequest) {
        Payment payment = paymentRepo.findById(paymentId).orElseThrow(() -> new PaymentNotFoundException("Payment Not Found with payment id: " + paymentId));
        payment.setPaymentStatus(paymentStatusUpdateRequest.getPaymentStatus());
        Payment savedPayment = paymentRepo.save(payment);
        return paymentMapper.toDto(savedPayment);
    }
}
