package com.ramarizdev.eventureBackend.auth.dto;

import lombok.Data;

@Data
public class LoginResponseDto {
    private String id;
    private String name;
    private String email;
    private String role;
    private String token;
}
