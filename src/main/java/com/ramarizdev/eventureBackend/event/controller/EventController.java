package com.ramarizdev.eventureBackend.event.controller;

import com.ramarizdev.eventureBackend.event.dto.EventRequestDto;
import com.ramarizdev.eventureBackend.event.entity.Event;
import com.ramarizdev.eventureBackend.event.service.impl.EventServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/events")
@Validated
public class EventController {
    private final EventServiceImpl eventService;

    public EventController(EventServiceImpl eventService) {
        this.eventService = eventService;
    }

    @GetMapping()
    public String getAllEvents() {
        return "get all events";
    }

    @PostMapping()
    public ResponseEntity<Event> createEvent(@RequestBody EventRequestDto requestDto) {
        Event event = eventService.createEvent(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }
}
