package com.ramarizdev.eventureBackend.user.dto;

import com.ramarizdev.eventureBackend.user.entity.ReferralCode;
import com.ramarizdev.eventureBackend.user.entity.User;
import com.ramarizdev.eventureBackend.user.entity.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.security.SecureRandom;

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

    public ReferralCode generateReferralCode() {
        ReferralCode referralCode = new ReferralCode();

        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        final int CODE_LENGTH = 6;
        final SecureRandom secureRandom = new SecureRandom();

        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CHARACTERS.charAt(secureRandom.nextInt(CHARACTERS.length())));
        }
        referralCode.setCode(code.toString());

        return referralCode;
    }
}
