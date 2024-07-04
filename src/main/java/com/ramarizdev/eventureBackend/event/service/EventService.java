package com.ramarizdev.eventureBackend.event.service;

import com.ramarizdev.eventureBackend.event.dto.EventDetailsDto;
import com.ramarizdev.eventureBackend.event.dto.EventRequestDto;
import com.ramarizdev.eventureBackend.event.dto.EventSummaryDto;
import org.springframework.data.domain.Page;

import java.nio.file.AccessDeniedException;

public interface EventService {
    Page<EventSummaryDto> getAllEvents(String categorySlug, String location, boolean isFree, String search, int page, int size);
    EventDetailsDto getEventDetails(Long id);
    EventSummaryDto createEvent(EventRequestDto eventRequestDto, Long organizerId);
    void deleteEvent(Long eventId, String currentUserEmail) throws AccessDeniedException;
}
