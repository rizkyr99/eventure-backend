package com.ramarizdev.eventureBackend.event.service.impl;

import com.ramarizdev.eventureBackend.event.dto.EventSummaryDto;
import com.ramarizdev.eventureBackend.event.entity.Event;
import com.ramarizdev.eventureBackend.event.service.OrganizerService;
import com.ramarizdev.eventureBackend.user.entity.Organizer;
import com.ramarizdev.eventureBackend.user.repository.OrganizerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizerServiceImpl implements OrganizerService {
    private final OrganizerRepository organizerRepository;

    public OrganizerServiceImpl(OrganizerRepository organizerRepository) {
        this.organizerRepository = organizerRepository;
    }

    @Override
    public List<EventSummaryDto> getOrganizerEvents(Long organizerId) {
        Organizer organizer = organizerRepository.findById(organizerId).orElseThrow(() -> new EntityNotFoundException("Organizer not found"));

        List<Event> events = organizer.getEvents();

        List<EventSummaryDto> eventSummaryDtos = events.stream().map(Event::toSummaryDto).collect(Collectors.toList());

        return eventSummaryDtos;
    }
}
