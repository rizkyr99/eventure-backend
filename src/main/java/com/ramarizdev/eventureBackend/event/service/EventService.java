package com.ramarizdev.eventureBackend.event.service;

import com.ramarizdev.eventureBackend.event.dto.EventRequestDto;
import com.ramarizdev.eventureBackend.event.dto.EventResponseDto;
import com.ramarizdev.eventureBackend.event.entity.Event;

import java.util.List;

public interface EventService {
    List<EventResponseDto> getAllEvents();
    EventResponseDto createEvent(EventRequestDto eventRequestDto);
}
