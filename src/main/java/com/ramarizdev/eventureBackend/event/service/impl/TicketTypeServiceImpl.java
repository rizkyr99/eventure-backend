package com.ramarizdev.eventureBackend.event.service.impl;

import com.ramarizdev.eventureBackend.event.entity.TicketType;
import com.ramarizdev.eventureBackend.event.repository.TicketTypeRepository;
import com.ramarizdev.eventureBackend.event.service.TicketTypeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TicketTypeServiceImpl implements TicketTypeService {
    private final TicketTypeRepository ticketTypeRepository;

    public TicketTypeServiceImpl(TicketTypeRepository ticketTypeRepository) {
        this.ticketTypeRepository = ticketTypeRepository;
    }

    @Override
    public TicketType getTicketTypeById(Long id) {
        return ticketTypeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Ticket type not found"));
    }

    @Override
    public TicketType reduceQuantity(TicketType ticketType) {
        if(ticketType.getQuantity() > 0) {
            ticketType.setQuantity(ticketType.getQuantity() - 1);
        }
        return ticketTypeRepository.save(ticketType);
    }
}
