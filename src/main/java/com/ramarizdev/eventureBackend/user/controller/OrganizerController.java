package com.ramarizdev.eventureBackend.user.controller;

import com.ramarizdev.eventureBackend.auth.service.impl.UserDetailsServiceImpl;
import com.ramarizdev.eventureBackend.event.dto.EventSummaryDto;
import com.ramarizdev.eventureBackend.event.service.impl.OrganizerServiceImpl;
import com.ramarizdev.eventureBackend.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/organizer")
public class OrganizerController {
    private final UserDetailsServiceImpl userDetailsService;
    private final OrganizerServiceImpl organizerService;

    public OrganizerController(UserDetailsServiceImpl userDetailsService, OrganizerServiceImpl organizerService) {
        this.userDetailsService = userDetailsService;
        this.organizerService = organizerService;
    }

    @GetMapping("/events")
    public ResponseEntity<Response<List<EventSummaryDto>>> getOrganizerEvents() {
        Long organizerId = userDetailsService.getOrganizerIdFromAuthentication();

        List<EventSummaryDto> eventSummaryDtos = organizerService.getOrganizerEvents(organizerId);

        return Response.success("Fetched events for organizer ID: " + organizerId, eventSummaryDtos);
    }
}
