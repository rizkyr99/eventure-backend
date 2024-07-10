package com.ramarizdev.eventureBackend.user.service.impl;

import com.ramarizdev.eventureBackend.user.entity.Attendee;
import com.ramarizdev.eventureBackend.user.entity.Point;
import com.ramarizdev.eventureBackend.user.service.PointService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

@Service
public class PointServiceImpl implements PointService {

    @Override
    public Point createPoint(Attendee attendee, Integer amount) {
        Point point = new Point();

        point.setAttendee(attendee);
        point.setAmount(amount);
        point.setCollectionDate(LocalDate.now());
        point.setExpirationDate(LocalDate.now().plusMonths(3));

        return point;
    }
}
