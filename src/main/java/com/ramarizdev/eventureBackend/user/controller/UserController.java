package com.ramarizdev.eventureBackend.user.controller;

import com.ramarizdev.eventureBackend.response.Response;
import com.ramarizdev.eventureBackend.user.dto.RegisterRequestDto;
import com.ramarizdev.eventureBackend.user.dto.UserDetailsDto;
import com.ramarizdev.eventureBackend.user.entity.User;
import com.ramarizdev.eventureBackend.user.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Validated
@Log
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
}
