package com.ramarizdev.eventureBackend.event.controller;

import com.ramarizdev.eventureBackend.event.dto.EventRequestDto;
import com.ramarizdev.eventureBackend.event.dto.EventResponseDto;
import com.ramarizdev.eventureBackend.event.entity.Event;
import com.ramarizdev.eventureBackend.event.service.impl.EventServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public ResponseEntity<List<EventResponseDto>> getAllEvents(@RequestParam(required = false) String category, @RequestParam(required = false) String location, @RequestParam(required = false, defaultValue = "false") boolean isFree) {
        List<EventResponseDto> events = eventService.getAllEvents(category, location, isFree);
        return ResponseEntity.ok().body(events);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EventResponseDto> createEvent(@Valid @ModelAttribute EventRequestDto requestDto) {
        EventResponseDto responseDto = eventService.createEvent(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
