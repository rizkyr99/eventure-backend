package com.ramarizdev.eventureBackend.event.dto;

import com.ramarizdev.eventureBackend.event.entity.Event;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class EventRequestDto {
    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String image;

    private LocalDate startDate;

    private LocalDate endDate;

    private String location;

    private String description;

    public Event toEntity() {
        Event event = new Event();
        event.setName(name);
        event.setImage(image);
        event.setStartDate(startDate);
        event.setEndDate(endDate);
        event.setLocation(location);
        event.setDescription(description);
        return event;
    }
}
