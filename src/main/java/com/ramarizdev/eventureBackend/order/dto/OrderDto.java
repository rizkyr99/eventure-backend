package com.ramarizdev.eventureBackend.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDto {
    private Long id;

    @NotNull
    private Long attendeeId;

    @NotNull
    private Long eventId;

    @NotNull
    @Size(min = 1, message = "Order must contain at least one item")
    private List<OrderItemDto> orderItems;

    @NotNull
    private BigDecimal totalPrice;

    @NotNull
    private boolean usePoints;
}
