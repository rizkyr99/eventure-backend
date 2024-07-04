package com.ramarizdev.eventureBackend.user.dto;

import lombok.Data;

@Data
public class AttendeeDto {
    private Long id;
    private String name;
    private String phone;
    private String image;
    private String gender;
    private String age;
    private String address;
    private String province;
    private String city;
    private String referralCode;
    private Integer totalPoints;
}
