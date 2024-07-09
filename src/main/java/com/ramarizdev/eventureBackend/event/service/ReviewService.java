package com.ramarizdev.eventureBackend.event.service;

import com.ramarizdev.eventureBackend.event.dto.ReviewDto;
import com.ramarizdev.eventureBackend.user.entity.Attendee;

import java.util.List;

public interface ReviewService {
    List<ReviewDto> getReviewsByEventId(Long eventId);
    ReviewDto createReview(ReviewDto reviewDto, Attendee attendee);
}
