package com.marketplace.order_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlacedEvent {

    private String eventId;
    private String orderId;
    private String skuCode;
    private Integer quantity;
    private LocalDateTime eventTime;

}
