package com.ramarizdev.eventureBackend.event.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EventSummaryDto {
    private Long id;

    private String name;

    private String image;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private String location;

    private String category;

}