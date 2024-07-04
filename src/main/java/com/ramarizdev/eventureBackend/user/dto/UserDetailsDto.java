package com.ramarizdev.eventureBackend.user.dto;

import com.ramarizdev.eventureBackend.user.entity.UserRole;
import lombok.Data;

@Data
public class UserDetailsDto {
    private Long id;
    private String email;
    private UserRole role;

    private AttendeeDto attendee;
    private OrganizerDto organizer;
}
