package com.ramarizdev.eventureBackend.event.service;

import com.ramarizdev.eventureBackend.event.dto.EventSummaryDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrganizerService {
    List<EventSummaryDto> getOrganizerEvents(Long organizerId);
}
