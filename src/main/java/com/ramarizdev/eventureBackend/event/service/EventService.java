package com.ramarizdev.eventureBackend.event.service;

import com.ramarizdev.eventureBackend.event.dto.EventDetailsDto;
import com.ramarizdev.eventureBackend.event.dto.EventRequestDto;
import com.ramarizdev.eventureBackend.event.dto.EventSummaryDto;
import com.ramarizdev.eventureBackend.event.entity.Event;
import org.springframework.data.domain.Page;

import java.nio.file.AccessDeniedException;

public interface EventService {
    Page<EventSummaryDto> getAllEvents(String categorySlug, String location, boolean isFree, String search, int page, int size);
    Event getEventDetails(Long id);
    EventSummaryDto createEvent(EventRequestDto eventRequestDto, Long organizerId);
    EventSummaryDto updateEvent(Long eventId, EventRequestDto eventRequestDto, String email) throws AccessDeniedException;
    void deleteEvent(Long eventId, String currentUserEmail) throws AccessDeniedException;
}
