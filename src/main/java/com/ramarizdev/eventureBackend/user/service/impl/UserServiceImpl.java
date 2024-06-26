package com.ramarizdev.eventureBackend.user.service.impl;

import com.ramarizdev.eventureBackend.user.dto.RegisterRequestDto;
import com.ramarizdev.eventureBackend.user.entity.Attendee;
import com.ramarizdev.eventureBackend.user.entity.User;
import com.ramarizdev.eventureBackend.user.entity.UserRole;
import com.ramarizdev.eventureBackend.user.repository.AttendeeRepository;
import com.ramarizdev.eventureBackend.user.repository.UserRepository;
import com.ramarizdev.eventureBackend.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AttendeeRepository attendeeRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, AttendeeRepository attendeeRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.attendeeRepository = attendeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User register(RegisterRequestDto requestDto) {
        User user = userRepository.save(requestDto.toEntity());
        var password = passwordEncoder.encode(requestDto.getPassword());
        user.setPassword(password);
        User newUser = userRepository.save(user);

        if(requestDto.getRole() == UserRole.ATTENDEE) {
            Attendee attendee = new Attendee();
            attendee.setName(requestDto.getName());
            attendee.setUser(newUser);
            attendeeRepository.save(attendee);
        }

        return newUser;
    }
}
