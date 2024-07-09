package com.ramarizdev.eventureBackend.user.service;

import com.ramarizdev.eventureBackend.user.entity.Attendee;

public interface AttendeeService {
    Attendee getAttendeeById(Long attendeeId);
    Attendee getAttendeeByEmail(String email);
}
