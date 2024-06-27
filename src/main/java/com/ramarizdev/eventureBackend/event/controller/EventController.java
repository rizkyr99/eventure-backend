package com.ramarizdev.eventureBackend.event.controller;

import com.ramarizdev.eventureBackend.event.dto.EventRequestDto;
import com.ramarizdev.eventureBackend.event.entity.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/events")
@Validated
public class EventController {

    @GetMapping()
    public String getAllEvents() {
        return "get all events";
    }

    @PostMapping()
    public ResponseEntity<Event> createEvent(@RequestBody EventRequestDto requestDto) {
        Event event = new Event();
        return ResponseEntity.ok().body(event);
    }
}
