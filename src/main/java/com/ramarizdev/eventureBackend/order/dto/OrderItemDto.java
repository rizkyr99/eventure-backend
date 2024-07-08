package com.ramarizdev.eventureBackend.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Long id;

    @NotNull
    private Long ticketTypeId;

    @NotNull
    private Integer quantity;

    @NotNull
    private BigDecimal price;
}
