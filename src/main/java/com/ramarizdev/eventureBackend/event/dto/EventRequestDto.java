package com.ramarizdev.eventureBackend.event.dto;

import com.ramarizdev.eventureBackend.event.entity.Event;
import com.ramarizdev.eventureBackend.event.entity.TicketType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

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

    private Long category;

    private List<TicketType> ticketTypes;

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
