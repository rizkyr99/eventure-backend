package com.ramarizdev.eventureBackend.event.repository;

import com.ramarizdev.eventureBackend.event.entity.Event;
import com.ramarizdev.eventureBackend.event.entity.Review;
import com.ramarizdev.eventureBackend.user.entity.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByAttendeeAndEvent(Attendee attendee, Event event);
}
