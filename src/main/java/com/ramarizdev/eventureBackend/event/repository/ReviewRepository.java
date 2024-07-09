package com.ramarizdev.eventureBackend.event.repository;

import com.ramarizdev.eventureBackend.event.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
