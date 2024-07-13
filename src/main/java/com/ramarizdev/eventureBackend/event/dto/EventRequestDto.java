package com.ramarizdev.eventureBackend.event.dto;

import com.ramarizdev.eventureBackend.event.entity.Event;
import com.ramarizdev.eventureBackend.event.entity.TicketType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class EventRequestDto {
    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String slug;

    @NotNull
    private MultipartFile image;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    @NotNull
    @NotBlank
    private String location;

    private String description;

    @NotNull
    private Long category;

    @NotNull
    private Boolean isFree;

    private List<TicketType> ticketTypes;

    public Event toEntity() {
        Event event = new Event();
        event.setName(name);
        event.setSlug(slug);
        event.setStartDate(startDate);
        event.setEndDate(endDate);
        event.setStartTime(startTime);
        event.setEndTime(endTime);
        event.setLocation(location);
        event.setDescription(description);
        event.setIsFree(isFree);
        return event;
    }
}
