package com.ramarizdev.eventureBackend.user.service.impl;

import com.ramarizdev.eventureBackend.user.entity.Attendee;
import com.ramarizdev.eventureBackend.user.entity.Point;
import com.ramarizdev.eventureBackend.user.repository.PointRepository;
import com.ramarizdev.eventureBackend.user.service.PointService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

@Service
public class PointServiceImpl implements PointService {
    private final PointRepository pointRepository;

    public PointServiceImpl(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    @Override
    public Point createPoint(Attendee attendee, Integer amount, boolean withExpiration) {
        Point point = new Point();

        point.setAttendee(attendee);
        point.setAmount(amount);
        point.setCollectionDate(LocalDate.now());
        if(withExpiration) {
            point.setExpirationDate(LocalDate.now().plusMonths(3));
        }

        return point;
    }

    @Override
    public Point createPoint(Attendee attendee, Integer amount) {
        return createPoint(attendee, amount, false);
    }

    @Override
    public Point usePoint(Attendee attendee, Integer amount) {
        Point point = createPoint(attendee, amount);
        return pointRepository.save(point);
    }


}
