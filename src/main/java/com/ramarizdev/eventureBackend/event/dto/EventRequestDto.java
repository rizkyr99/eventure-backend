package com.ramarizdev.eventureBackend.event.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class EventRequestDto {
    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String image;

    private Date startDate;

    private Date endDate;

    private String location;

    private String description;
}
