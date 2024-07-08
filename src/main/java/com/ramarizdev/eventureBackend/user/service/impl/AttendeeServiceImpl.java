package com.ramarizdev.eventureBackend.user.service.impl;

import com.ramarizdev.eventureBackend.user.entity.Attendee;
import com.ramarizdev.eventureBackend.user.repository.AttendeeRepository;
import com.ramarizdev.eventureBackend.user.service.AttendeeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AttendeeServiceImpl implements AttendeeService {
    private final AttendeeRepository attendeeRepository;

    public AttendeeServiceImpl(AttendeeRepository attendeeRepository) {
        this.attendeeRepository = attendeeRepository;
    }

    @Override
    public Attendee getAttendeeById(Long attendeeId) {
        return attendeeRepository.findById(attendeeId).orElseThrow(() -> new EntityNotFoundException("Attendee not found"));
    }
}
