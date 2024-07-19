package com.ramarizdev.eventureBackend.order.service;

import com.ramarizdev.eventureBackend.order.dto.OrderDto;
import com.ramarizdev.eventureBackend.order.entity.Order;

public interface OrderService {
    boolean hasUsedReferralVoucher(Long attendeeId);
    OrderDto createOrder(OrderDto orderDto, String email);
}
