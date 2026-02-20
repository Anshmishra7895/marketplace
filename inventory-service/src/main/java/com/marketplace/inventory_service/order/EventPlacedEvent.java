package com.marketplace.inventory_service.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventPlacedEvent {

    private String eventId;
    private String orderId;
    private String skuCode;
    private Integer quantity;
    private LocalDateTime eventTime;

}
