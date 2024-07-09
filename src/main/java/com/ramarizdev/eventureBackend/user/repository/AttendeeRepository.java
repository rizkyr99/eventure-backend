package com.ramarizdev.eventureBackend.user.repository;

import com.ramarizdev.eventureBackend.user.entity.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendeeRepository extends JpaRepository<Attendee, Long> {
    Optional<Attendee> findByUserEmail(String email);
}
