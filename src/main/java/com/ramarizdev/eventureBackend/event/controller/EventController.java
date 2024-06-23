package com.ramarizdev.eventureBackend.event.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/events")
@Validated
public class EventController {

    @GetMapping()
    public String getAllEvents() {
        return "get all events";
    }
}
