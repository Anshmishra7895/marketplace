package com.example.payment_service.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TransactionGenerator {

    public static String generate(){
        String transactionId = "txn-" + UUID.randomUUID().toString();
        return transactionId;
    }

}
