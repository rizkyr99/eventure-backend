package com.ramarizdev.eventureBackend.event.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TicketTypeDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer quantity;
}
