package com.ramarizdev.eventureBackend.user.service;

import com.ramarizdev.eventureBackend.user.dto.RegisterRequestDto;
import com.ramarizdev.eventureBackend.user.dto.UserDetailsDto;
import com.ramarizdev.eventureBackend.user.entity.User;

public interface UserService {
    User register(RegisterRequestDto requestDto);
    UserDetailsDto getUserProfile(String email);
}
