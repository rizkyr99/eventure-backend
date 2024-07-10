package com.ramarizdev.eventureBackend.user.service.impl;

import com.ramarizdev.eventureBackend.user.dto.RegisterRequestDto;
import com.ramarizdev.eventureBackend.user.dto.UserDetailsDto;
import com.ramarizdev.eventureBackend.user.entity.*;
import com.ramarizdev.eventureBackend.user.repository.AttendeeRepository;
import com.ramarizdev.eventureBackend.user.repository.OrganizerRepository;
import com.ramarizdev.eventureBackend.user.repository.ReferralCodeRepository;
import com.ramarizdev.eventureBackend.user.repository.UserRepository;
import com.ramarizdev.eventureBackend.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final AttendeeRepository attendeeRepository;
    private final OrganizerRepository organizerRepository;
    private final ReferralCodeRepository referralCodeRepository;
    private final PasswordEncoder passwordEncoder;
    private final PointServiceImpl pointService;

    public UserServiceImpl(UserRepository userRepository, AttendeeRepository attendeeRepository, OrganizerRepository organizerRepository, ReferralCodeRepository referralCodeRepository, PasswordEncoder passwordEncoder, PointServiceImpl pointService) {
        this.userRepository = userRepository;
        this.attendeeRepository = attendeeRepository;
        this.organizerRepository = organizerRepository;
        this.referralCodeRepository = referralCodeRepository;
        this.passwordEncoder = passwordEncoder;
        this.pointService = pointService;
    }

    @Transactional
    public User register(RegisterRequestDto requestDto) {
        User user = requestDto.toEntity();
        String password = passwordEncoder.encode(requestDto.getPassword());
        user.setPassword(password);

        if(requestDto.getRole() == UserRole.ATTENDEE) {
            Attendee attendee = new Attendee();
            attendee.setName(requestDto.getName());
            attendee.setUser(user);

            if(requestDto.getReferralCode() != null) {
                Optional<ReferralCode> referralCode = referralCodeRepository.findByCode(requestDto.getReferralCode());
                if(referralCode.isPresent() && referralCode.get().getAttendee() != null) {
                    ReferralUsage referralUsage = new ReferralUsage();
                    referralUsage.setReferralCode(referralCode.get());
                    referralUsage.setReferredAttendee(attendee);

                    attendee.setReferralUsage(referralUsage);

                    Attendee referrerAttendee = referralCode.get().getAttendee();

                    Point point = pointService.createPoint(referrerAttendee, 10000, true);

                    referrerAttendee.getPoints().add(point);

                    attendeeRepository.save(referrerAttendee);
                }


            }

            ReferralCode newReferralCode = requestDto.generateReferralCode();
            newReferralCode.setAttendee(attendee);

            attendee.setReferralCode(newReferralCode);
            user.setAttendee(attendee);
        } else {
            Organizer organizer = new Organizer();
            organizer.setName(requestDto.getName());
            organizer.setUser(user);

            user.setOrganizer(organizer);
        }

        return userRepository.save(user);
    }

    @Override
    public UserDetailsDto getUserProfile(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));

        UserDetailsDto userDetailsDto = user.toDto();

        return userDetailsDto;
    }
}
