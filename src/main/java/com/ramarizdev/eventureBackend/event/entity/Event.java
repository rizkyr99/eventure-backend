package com.ramarizdev.eventureBackend.event.entity;

import com.ramarizdev.eventureBackend.category.entity.Category;
import com.ramarizdev.eventureBackend.event.dto.EventResponseDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_id_gen")
    @SequenceGenerator(name = "event_id_gen", sequenceName = "event_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @NotNull
    @NotBlank
    @Column(name = "name")
    private String name;

    @NotNull
    @NotBlank
    @Column(name = "image")
    private String image;

    @NotNull
    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate endDate;

    @NotNull
    @Column(name = "location", nullable = false)
    private String location;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "is_free", nullable = false)
    private Boolean isFree = false;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<TicketType> ticketTypes;

    public EventResponseDto toDto() {
        EventResponseDto responseDto = new EventResponseDto();

        responseDto.setId(id);
        responseDto.setName(name);
        responseDto.setImage(image);
        responseDto.setStartDate(startDate);
        responseDto.setEndDate(endDate);
        responseDto.setLocation(location);
        responseDto.setDescription(description);
        responseDto.setCategory(category.getId().toString());
        return responseDto;
    }
}
