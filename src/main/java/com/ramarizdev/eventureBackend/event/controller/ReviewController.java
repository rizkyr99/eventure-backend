package com.ramarizdev.eventureBackend.event.controller;

import com.ramarizdev.eventureBackend.auth.service.impl.UserDetailsServiceImpl;
import com.ramarizdev.eventureBackend.event.dto.ReviewDto;
import com.ramarizdev.eventureBackend.event.service.impl.ReviewServiceImpl;
import com.ramarizdev.eventureBackend.response.Response;
import com.ramarizdev.eventureBackend.user.entity.Attendee;
import com.ramarizdev.eventureBackend.user.service.impl.AttendeeServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reviews")
@Validated
public class ReviewController {
    private final ReviewServiceImpl reviewService;
    private final AttendeeServiceImpl attendeeService;

    public ReviewController(ReviewServiceImpl reviewService, UserDetailsServiceImpl userDetailsService, AttendeeServiceImpl attendeeService) {
        this.reviewService = reviewService;
        this.attendeeService = attendeeService;
    }

    @PostMapping()
    public ResponseEntity<Response<ReviewDto>> createReview(@Valid @RequestBody ReviewDto reviewDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Attendee attendee = attendeeService.getAttendeeByEmail(email);

        ReviewDto response = reviewService.createReview(reviewDto, attendee);

        return Response.success(HttpStatus.CREATED.value(), "Review created successfully", response);
    }
}
