package com.ramarizdev.eventureBackend.order.service;

import com.ramarizdev.eventureBackend.order.dto.OrderDto;
import com.ramarizdev.eventureBackend.order.entity.Order;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto, String email);
}
