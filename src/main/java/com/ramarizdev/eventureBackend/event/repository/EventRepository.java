package com.ramarizdev.eventureBackend.event.repository;

import com.ramarizdev.eventureBackend.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    Optional<Event> findBySlug(String slug);
    @Query("SELECT e FROM Event e WHERE e.startDate >= :now ORDER BY e.startDate ASC")
    List<Event> findUpcomingEvents(LocalDate now);
    @Query("SELECT MIN(tt.price) FROM Event e JOIN e.ticketTypes tt WHERE e.id = :eventId")
    Optional<Double> findLowestTicketPrice(@Param("eventId") Long eventId);

}
