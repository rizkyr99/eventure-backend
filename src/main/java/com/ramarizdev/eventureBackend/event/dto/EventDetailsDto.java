package com.ramarizdev.eventureBackend.event.dto;

import com.ramarizdev.eventureBackend.category.dto.CategoryResponseDto;
import com.ramarizdev.eventureBackend.user.dto.OrganizerDto;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EventDetailsDto {
    private Long id;
    private String name;
    private String slug;
    private String image;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;
    private String description;
    private CategoryResponseDto category;
    private Boolean isFree;
    private Double lowestPrice;
    private OrganizerDto organizer;
}
