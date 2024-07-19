package com.ramarizdev.eventureBackend.user.controller;

import com.ramarizdev.eventureBackend.event.dto.EventSummaryDto;
import com.ramarizdev.eventureBackend.event.service.impl.EventServiceImpl;
import com.ramarizdev.eventureBackend.response.Response;
import com.ramarizdev.eventureBackend.user.dto.RegisterRequestDto;
import com.ramarizdev.eventureBackend.user.dto.UserDetailsDto;
import com.ramarizdev.eventureBackend.user.entity.Attendee;
import com.ramarizdev.eventureBackend.user.entity.User;
import com.ramarizdev.eventureBackend.user.service.UserService;
import com.ramarizdev.eventureBackend.user.service.impl.AttendeeServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Validated
@Log
public class UserController {
    private final UserService userService;
    private final EventServiceImpl eventService;
    private final AttendeeServiceImpl attendeeService;

    public UserController(UserService userService, EventServiceImpl eventService, AttendeeServiceImpl attendeeService) {
        this.userService = userService;
        this.eventService = eventService;
        this.attendeeService = attendeeService;
    }

    @PostMapping("/register")
    public ResponseEntity<Response<User>> register(@Valid @RequestBody RegisterRequestDto requestDto) {
        User user = userService.register(requestDto);
        return Response.success("User registered successfully", user);
    }

    @GetMapping("/profile")
    public ResponseEntity<Response<UserDetailsDto>> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        UserDetailsDto userDetailsDto = userService.getUserProfile(email);

        return Response.success("User profile fetched", userDetailsDto);
    }

    @GetMapping("/events")
    public ResponseEntity<Response<List<EventSummaryDto>>> getUserEvents() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Attendee attendee = attendeeService.getAttendeeByEmail(email);

        List<EventSummaryDto> events = eventService.getUserEvents(attendee);
        return Response.success("List of user events fetched", events);
    }
}
