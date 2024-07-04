package com.ramarizdev.eventureBackend.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ramarizdev.eventureBackend.user.dto.AttendeeDto;
import com.ramarizdev.eventureBackend.user.dto.OrganizerDto;
import com.ramarizdev.eventureBackend.user.dto.UserDetailsDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_gen")
    @SequenceGenerator(name = "user_id_gen", sequenceName = "user_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @NotBlank
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @NotBlank
    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Attendee attendee;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Organizer organizer;

    public UserDetailsDto toDto() {
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setId(id);
        userDetailsDto.setEmail(email);
        userDetailsDto.setRole(role);

        if(role.equals(UserRole.ATTENDEE)) {
            AttendeeDto attendeeDto = attendee.toDto();
            userDetailsDto.setAttendee(attendeeDto);
        } else if(role.equals(UserRole.ORGANIZER)) {
            OrganizerDto organizerDto = organizer.toDto();
            userDetailsDto.setOrganizer(organizerDto);
        }


        return userDetailsDto;
    }
}

