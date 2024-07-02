package com.ramarizdev.eventureBackend.event.controller;

import com.ramarizdev.eventureBackend.auth.service.impl.UserDetailsServiceImpl;
import com.ramarizdev.eventureBackend.event.dto.EventRequestDto;
import com.ramarizdev.eventureBackend.event.dto.EventResponseDto;
import com.ramarizdev.eventureBackend.event.service.impl.EventServiceImpl;
import com.ramarizdev.eventureBackend.response.Response;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@Validated
public class EventController {
    private static final Logger log = LoggerFactory.getLogger(EventController.class);
    private final EventServiceImpl eventService;
    private final UserDetailsServiceImpl userDetailsService;

    public EventController(EventServiceImpl eventService, UserDetailsServiceImpl userDetailsService) {
        this.eventService = eventService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping()
    public ResponseEntity<Response<Page<EventResponseDto>>> getAllEvents(@RequestParam(required = false) String category, @RequestParam(required = false) String location, @RequestParam(required = false, defaultValue = "false") boolean isFree, @RequestParam(required = false) String search, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        Page<EventResponseDto> events = eventService.getAllEvents(category, location, isFree, search, page - 1, size);
        return Response.success("List of events fetched", events);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<EventResponseDto>> createEvent(@Valid @ModelAttribute EventRequestDto requestDto) {
        Long organizerId = userDetailsService.getOrganizerIdFromAuthentication();

        EventResponseDto responseDto = eventService.createEvent(requestDto, organizerId);
        return Response.success(HttpStatus.CREATED.value(), "Event created successfully", responseDto);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long eventId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
//        User user = userRepository

        eventService.deleteEvent(eventId, email);
        return Response.success("Event deleted successfully");
    }


}
