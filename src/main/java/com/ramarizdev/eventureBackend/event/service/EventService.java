package com.ramarizdev.eventureBackend.event.service;

import com.ramarizdev.eventureBackend.event.dto.EventRequestDto;
import com.ramarizdev.eventureBackend.event.dto.EventResponseDto;
import com.ramarizdev.eventureBackend.event.entity.Event;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EventService {
    Page<EventResponseDto> getAllEvents(String categorySlug, String location, boolean isFree, String search, int page, int size);
    EventResponseDto createEvent(EventRequestDto eventRequestDto, Long organizerId);
    void deleteEvent(Long eventId, String currentUserEmail);
}
