package com.ramarizdev.eventureBackend.order.repository;

import com.ramarizdev.eventureBackend.event.entity.Event;
import com.ramarizdev.eventureBackend.order.entity.Order;
import com.ramarizdev.eventureBackend.user.entity.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByAttendeeAndEvent(Attendee attendee, Event event);
}
