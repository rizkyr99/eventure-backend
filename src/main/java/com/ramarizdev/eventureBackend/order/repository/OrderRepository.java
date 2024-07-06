package com.ramarizdev.eventureBackend.order.repository;

import com.ramarizdev.eventureBackend.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
