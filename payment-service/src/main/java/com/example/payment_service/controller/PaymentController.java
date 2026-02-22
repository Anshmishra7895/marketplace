package com.example.payment_service.controller;

import com.example.payment_service.dto.PaymentRequestDto;
import com.example.payment_service.dto.PaymentResponseDto;
import com.example.payment_service.dto.PaymentStatusUpdateRequestDto;
import com.example.payment_service.service.PaymentService;
import com.example.payment_service.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createPayment(@RequestBody PaymentRequestDto paymentRequestDto){
        PaymentResponseDto payment = paymentService.createPayment(paymentRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(
                true, "Payment created", payment, LocalDateTime.now())
        );
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse> getPaymentByOrderId(@PathVariable String orderId){
        PaymentResponseDto payment = paymentService.getPaymentByOrderId(orderId);
        return new ResponseEntity<>(new ApiResponse(
                true,
                "Payment fetched successfully with order Id: "+orderId,
                payment,
                LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<ApiResponse> updatePaymentStatus(@PathVariable Long paymentId, @RequestBody PaymentStatusUpdateRequestDto paymentStatusUpdateRequestDto){
        PaymentResponseDto updatedPaymentStatus = paymentService.updatePaymentStatus(paymentId, paymentStatusUpdateRequestDto);
        return ResponseEntity.ok(new ApiResponse(
                true, "Payment Status updated", updatedPaymentStatus, LocalDateTime.now()
        ));
    }

}
