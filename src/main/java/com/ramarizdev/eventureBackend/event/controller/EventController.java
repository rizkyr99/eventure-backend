package com.ramarizdev.eventureBackend.event.controller;

import com.cloudinary.Cloudinary;
import com.ramarizdev.eventureBackend.auth.service.impl.UserDetailsServiceImpl;
import com.ramarizdev.eventureBackend.event.dto.*;
import com.ramarizdev.eventureBackend.event.entity.Event;
import com.ramarizdev.eventureBackend.event.entity.TicketType;
import com.ramarizdev.eventureBackend.event.service.impl.EventServiceImpl;
import com.ramarizdev.eventureBackend.event.service.impl.ReviewServiceImpl;
import com.ramarizdev.eventureBackend.response.Response;
import com.ramarizdev.eventureBackend.voucher.entity.Voucher;
import com.ramarizdev.eventureBackend.voucher.service.impl.VoucherServiceImpl;
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
import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@Validated
public class EventController {
    private final EventServiceImpl eventService;
    private final ReviewServiceImpl reviewService;
    private final UserDetailsServiceImpl userDetailsService;
    private final VoucherServiceImpl voucherService;
    private final Cloudinary cloudinary;

    public EventController(EventServiceImpl eventService, ReviewServiceImpl reviewService, UserDetailsServiceImpl userDetailsService, VoucherServiceImpl voucherService, Cloudinary cloudinary) {
        this.eventService = eventService;
        this.reviewService = reviewService;
        this.userDetailsService = userDetailsService;
        this.voucherService = voucherService;
        this.cloudinary = cloudinary;
    }

    @GetMapping()
    public ResponseEntity<Response<Page<EventSummaryDto>>> getAllEvents(@RequestParam(required = false) String category, @RequestParam(required = false) String location, @RequestParam(required = false) Boolean isFree, @RequestParam(required = false) String search, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        Page<EventSummaryDto> events = eventService.getAllEvents(category, location, isFree, search, page - 1, size);
        return Response.success("List of events fetched", events);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<Response<List<EventSummaryDto>>> getUpcomingEvents() {
        List<EventSummaryDto> events = eventService.getUpcomingEvents();

        return Response.success("List of upcoming events fetched", events);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Response<EventDetailsDto>> getEventDetails(@PathVariable Long eventId) {
        Event event = eventService.getEventDetails(eventId);

        EventDetailsDto eventDetailsDto = event.toDetailsDto();
        String imageUrl = cloudinary.url().format("png").secure(true).generate(event.getImage());
        eventDetailsDto.setImage(imageUrl);

        return Response.success("Event details fetched", eventDetailsDto);
    }

    @GetMapping("/slug/{eventSlug}")
    public ResponseEntity<Response<EventDetailsDto>> getEventBySlug(@PathVariable String eventSlug) {
        Event event = eventService.getEventBySlug(eventSlug);

        EventDetailsDto eventDetailsDto = event.toDetailsDto();
        String imageUrl = cloudinary.url().format("png").secure(true).generate(event.getImage());
        eventDetailsDto.setImage(imageUrl);

        return Response.success("Event details fetched", eventDetailsDto);
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

    @GetMapping("/{eventId}/tickets")
    public ResponseEntity<Response<List<TicketTypeDto>>> getTicketTypesByEventId(@PathVariable Long eventId) {
        List<TicketTypeDto> ticketTypes = eventService.getTicketTypesByEventId(eventId);

        return Response.success("Successfully fetched ticket types for event with ID: " + eventId, ticketTypes);
    }

    @GetMapping("/{eventId}/reviews")
    public ResponseEntity<Response<List<ReviewDto>>> getReviewsByEventId(@PathVariable Long eventId) {
        List<ReviewDto> reviewDtos = eventService.getEventReviews(eventId);
        return Response.success("Successfully fetched review for event with ID: " + eventId, reviewDtos);
    }

    @GetMapping("/{eventId}/vouchers")
    public ResponseEntity<Response<List<Voucher>>> getAllVouchers(@PathVariable Long eventId) {
        List<Voucher> vouchers = voucherService.getAllVouchers(eventId);
        return Response.success("List of vouchers fetched", vouchers);
    }

}
