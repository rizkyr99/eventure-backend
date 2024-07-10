package com.ramarizdev.eventureBackend.user.service;

import com.ramarizdev.eventureBackend.user.entity.Attendee;
import com.ramarizdev.eventureBackend.user.entity.Point;

public interface PointService {
    Point createPoint(Attendee attendee, Integer amount, boolean withExpiration);
    Point createPoint(Attendee attendee, Integer amount);
    Point usePoint(Attendee attendee, Integer amount);
}
