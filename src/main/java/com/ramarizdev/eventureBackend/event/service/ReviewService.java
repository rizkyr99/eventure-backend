package com.ramarizdev.eventureBackend.event.service;

import com.ramarizdev.eventureBackend.event.dto.ReviewDto;
import com.ramarizdev.eventureBackend.user.entity.Attendee;

public interface ReviewService {
    ReviewDto createReview(ReviewDto reviewDto, Attendee attendee);
}
