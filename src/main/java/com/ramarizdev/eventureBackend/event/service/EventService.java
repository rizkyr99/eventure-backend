package com.ramarizdev.eventureBackend.event.service;

import com.ramarizdev.eventureBackend.event.dto.*;
import com.ramarizdev.eventureBackend.event.entity.Event;
import com.ramarizdev.eventureBackend.event.entity.Review;
import com.ramarizdev.eventureBackend.event.entity.TicketType;
import com.ramarizdev.eventureBackend.user.entity.Attendee;
import org.springframework.data.domain.Page;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface EventService {
    Page<EventSummaryDto> getAllEvents(String categorySlug, String location, Boolean isFree, String search, int page, int size);
    List<EventSummaryDto> getUpcomingEvents();
    Event getEventDetails(Long id);
    Event getEventBySlug(String slug);
    EventSummaryDto createEvent(EventRequestDto eventRequestDto, Long organizerId);
    EventSummaryDto updateEvent(Long eventId, EventRequestDto eventRequestDto, String email) throws AccessDeniedException;
    List<ReviewDto> getEventReviews(Long eventId);
    List<TicketTypeDto> getTicketTypesByEventId(Long eventId);
    List<EventSummaryDto> getUserEvents(Attendee attendee);
    void deleteEvent(Long eventId, String currentUserEmail) throws AccessDeniedException;
}
