package com.ramarizdev.eventureBackend.user.repository;

import com.ramarizdev.eventureBackend.user.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {
}
