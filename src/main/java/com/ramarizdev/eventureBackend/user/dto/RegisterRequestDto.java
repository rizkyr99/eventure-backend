package com.ramarizdev.eventureBackend.user.dto;

import com.ramarizdev.eventureBackend.user.entity.User;
import com.ramarizdev.eventureBackend.user.entity.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterRequestDto implements Serializable {
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Role is required")
    private UserRole role;

    @NotBlank(message = "Password is required")
    private String password;

    private String referralCode;

    public User toEntity() {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
//
//        if(role == UserRole.ATTENDEE) {
//            Attendee attendee = new Attendee();
//            attendee.setName(name);
//            attendee.setUser(user);
//        }

        return user;
    }
}
