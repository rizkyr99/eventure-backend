package com.ramarizdev.eventureBackend.event.dto;

import com.ramarizdev.eventureBackend.event.entity.TicketType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class EventResponseDto {
    private Long id;
    private String name;

    private String image;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private String location;

    private String description;

    private String category;
}
