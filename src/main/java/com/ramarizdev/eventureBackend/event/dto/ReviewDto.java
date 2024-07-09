package com.ramarizdev.eventureBackend.event.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewDto {
    @NotNull
    @NotBlank
    private String content;

    @NotNull
    private Integer rating;

    @NotNull
    private Long eventId;

    private Long attendeeId;
}
