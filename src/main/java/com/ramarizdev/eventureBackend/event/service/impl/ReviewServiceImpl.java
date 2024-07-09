package com.ramarizdev.eventureBackend.event.service.impl;

import com.ramarizdev.eventureBackend.event.dto.ReviewDto;
import com.ramarizdev.eventureBackend.event.entity.Event;
import com.ramarizdev.eventureBackend.event.entity.Review;
import com.ramarizdev.eventureBackend.event.repository.ReviewRepository;
import com.ramarizdev.eventureBackend.event.service.ReviewService;
import com.ramarizdev.eventureBackend.user.entity.Attendee;
import com.ramarizdev.eventureBackend.user.service.impl.AttendeeServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final EventServiceImpl eventService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, AttendeeServiceImpl attendeeService, EventServiceImpl eventService) {
        this.reviewRepository = reviewRepository;
        this.eventService = eventService;
    }

    @Override
    public List<ReviewDto> getReviewsByEventId(Long eventId) {
        return List.of();
    }

    @Override
    public ReviewDto createReview(ReviewDto reviewDto, Attendee attendee) {
        Review review = new Review();

        review.setContent(reviewDto.getContent());
        review.setRating(reviewDto.getRating());

        Event event = eventService.getEventDetails(reviewDto.getEventId());
        review.setEvent(event);

        review.setAttendee(attendee);

        Review newReview = reviewRepository.save(review);

        return newReview.toDto();
    }
}
