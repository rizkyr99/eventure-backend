package com.ramarizdev.eventureBackend.event.controller;

import com.ramarizdev.eventureBackend.auth.service.impl.UserDetailsServiceImpl;
import com.ramarizdev.eventureBackend.event.dto.EventDetailsDto;
import com.ramarizdev.eventureBackend.event.dto.EventRequestDto;
import com.ramarizdev.eventureBackend.event.dto.EventSummaryDto;
import com.ramarizdev.eventureBackend.event.service.impl.EventServiceImpl;
import com.ramarizdev.eventureBackend.response.Response;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api/v1/events")
@Validated
public class EventController {
    private final EventServiceImpl eventService;
    private final UserDetailsServiceImpl userDetailsService;

    public EventController(EventServiceImpl eventService, UserDetailsServiceImpl userDetailsService) {
        this.eventService = eventService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping()
    public ResponseEntity<Response<Page<EventSummaryDto>>> getAllEvents(@RequestParam(required = false) String category, @RequestParam(required = false) String location, @RequestParam(required = false, defaultValue = "false") boolean isFree, @RequestParam(required = false) String search, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        Page<EventSummaryDto> events = eventService.getAllEvents(category, location, isFree, search, page - 1, size);
        return Response.success("List of events fetched", events);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Response<EventDetailsDto>> getEventDetails(@PathVariable Long eventId) {
        EventDetailsDto event = eventService.getEventDetails(eventId);

        return Response.success("Event details fetched", event);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<EventSummaryDto>> createEvent(@Valid @ModelAttribute EventRequestDto requestDto) {
        Long organizerId = userDetailsService.getOrganizerIdFromAuthentication();

        EventSummaryDto responseDto = eventService.createEvent(requestDto, organizerId);
        return Response.success(HttpStatus.CREATED.value(), "Event created successfully", responseDto);
    }

    @PutMapping(value = "/{eventId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<EventSummaryDto>> updateEvent(@Valid @PathVariable Long eventId, @ModelAttribute EventRequestDto requestDto) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        EventSummaryDto responseDto = eventService.updateEvent(eventId, requestDto, email);

        return Response.success("Event successfully updated", responseDto);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long eventId) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        eventService.deleteEvent(eventId, email);
        return Response.success("Event deleted successfully");
    }


}
