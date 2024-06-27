package com.ramarizdev.eventureBackend.user.service.impl;

import com.ramarizdev.eventureBackend.user.dto.RegisterRequestDto;
import com.ramarizdev.eventureBackend.user.entity.*;
import com.ramarizdev.eventureBackend.user.repository.AttendeeRepository;
import com.ramarizdev.eventureBackend.user.repository.OrganizerRepository;
import com.ramarizdev.eventureBackend.user.repository.ReferralCodeRepository;
import com.ramarizdev.eventureBackend.user.repository.UserRepository;
import com.ramarizdev.eventureBackend.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AttendeeRepository attendeeRepository;
    private final OrganizerRepository organizerRepository;
    private final ReferralCodeRepository referralCodeRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, AttendeeRepository attendeeRepository, OrganizerRepository organizerRepository, ReferralCodeRepository referralCodeRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.attendeeRepository = attendeeRepository;
        this.organizerRepository = organizerRepository;
        this.referralCodeRepository = referralCodeRepository;
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

            ReferralCode referralCode = requestDto.generateReferralCode();
            referralCode.setAttendee(attendee);
            referralCodeRepository.save(referralCode);

            attendee.setReferralCode(referralCode);

            attendeeRepository.save(attendee);
        } else {
            Organizer organizer = new Organizer();
            organizer.setName(requestDto.getName());
            organizer.setUser(newUser);
            organizerRepository.save(organizer);
        }

        return newUser;
    }
}
