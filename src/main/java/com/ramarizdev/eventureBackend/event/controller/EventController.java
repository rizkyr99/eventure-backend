package com.ramarizdev.eventureBackend.event.controller;

import com.ramarizdev.eventureBackend.event.dto.EventRequestDto;
import com.ramarizdev.eventureBackend.event.entity.Event;
import com.ramarizdev.eventureBackend.event.service.impl.EventServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@Validated
public class EventController {
    private static final Logger log = LoggerFactory.getLogger(EventController.class);
    private final EventServiceImpl eventService;

    public EventController(EventServiceImpl eventService) {
        this.eventService = eventService;
    }

    @GetMapping()
    public ResponseEntity<List<Event>> getAllEvents(@RequestParam(required = false) String category, @RequestParam(required = false) String location) {
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok().body(events);
    }

    @PostMapping()
    public ResponseEntity<Event> createEvent(@RequestBody EventRequestDto requestDto) {
        Event event = eventService.createEvent(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }
}
