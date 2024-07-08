package com.ramarizdev.eventureBackend.event.service;

import com.ramarizdev.eventureBackend.event.entity.TicketType;

public interface TicketTypeService {
    TicketType getTicketTypeById(Long id);
    TicketType reduceQuantity(TicketType ticketType);
}
