package com.ramarizdev.eventureBackend.event.service.impl;

import com.ramarizdev.eventureBackend.category.entity.Category;
import com.ramarizdev.eventureBackend.category.repository.CategoryRepository;
import com.ramarizdev.eventureBackend.event.dto.EventRequestDto;
import com.ramarizdev.eventureBackend.event.entity.Event;
import com.ramarizdev.eventureBackend.event.entity.TicketType;
import com.ramarizdev.eventureBackend.event.repository.EventRepository;
import com.ramarizdev.eventureBackend.event.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;

    public EventServiceImpl(EventRepository eventRepository, CategoryRepository categoryRepository) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event createEvent(EventRequestDto requestDto) {
        Event event = requestDto.toEntity();

        Category category = categoryRepository.findById(requestDto.getCategory()).orElseThrow(
                () -> new IllegalArgumentException("Invalid category ID")
        );

        event.setCategory(category);

        List<TicketType> ticketTypes = requestDto.getTicketTypes().stream().map(
                ticketType -> {
                    TicketType ticketType1 = new TicketType();
                    ticketType1.setName(ticketType.getName());
                    ticketType1.setPrice(ticketType.getPrice());
                    ticketType1.setQuantity(ticketType.getQuantity());
                    ticketType1.setEvent(event);
                    return ticketType1;
                }).collect(Collectors.toList());

        event.setTicketTypes(ticketTypes);

        Event newEvent = eventRepository.save(event);
        return newEvent;
    }
}
