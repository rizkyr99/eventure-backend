package com.ramarizdev.eventureBackend.event.service;

import com.ramarizdev.eventureBackend.event.dto.EventRequestDto;
import com.ramarizdev.eventureBackend.event.entity.Event;

public interface EventService {
    Event createEvent(EventRequestDto eventRequestDto);
}
