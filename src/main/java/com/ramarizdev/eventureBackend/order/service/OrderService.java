package com.ramarizdev.eventureBackend.order.service;

import com.ramarizdev.eventureBackend.order.entity.Order;

public interface OrderService {
    Order createOrder(Long userId, Long eventId, int quantity);
}
