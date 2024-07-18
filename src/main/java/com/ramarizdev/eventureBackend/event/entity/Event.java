package com.ramarizdev.eventureBackend.event.entity;

import com.ramarizdev.eventureBackend.category.entity.Category;
import com.ramarizdev.eventureBackend.event.dto.EventDetailsDto;
import com.ramarizdev.eventureBackend.event.dto.EventSummaryDto;
import com.ramarizdev.eventureBackend.order.entity.Order;
import com.ramarizdev.eventureBackend.user.dto.OrganizerDto;
import com.ramarizdev.eventureBackend.user.entity.Organizer;
import com.ramarizdev.eventureBackend.voucher.entity.Voucher;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @NotBlank
    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @NotNull
    @NotBlank
    @Column(name = "image", nullable = false)
    private String image;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @NotNull
    @Column(name = "location", nullable = false)
    private String location;

    @NotNull
    @Column(name = "description", nullable = false, columnDefinition = "text")
    private String description;

    @NotNull
    @Column(name = "is_free", nullable = false)
    private Boolean isFree;

    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    private Organizer organizer;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<TicketType> ticketTypes = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Voucher> vouchers = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    public EventSummaryDto toSummaryDto() {
        EventSummaryDto responseDto = new EventSummaryDto();

        responseDto.setId(id);
        responseDto.setName(name);
        responseDto.setSlug(slug);
        responseDto.setImage(image);
        responseDto.setStartDate(startDate);
        responseDto.setEndDate(endDate);
        responseDto.setStartTime(startTime);
        responseDto.setEndTime(endTime);
        responseDto.setLocation(location);
        responseDto.setIsFree(isFree);
        responseDto.setCategory(category.getName());

        return responseDto;
    }

    public EventDetailsDto toDetailsDto() {
        EventDetailsDto responseDto = new EventDetailsDto();

        responseDto.setId(id);
        responseDto.setName(name);
        responseDto.setSlug(slug);
        responseDto.setImage(image);
        responseDto.setStartDate(startDate);
        responseDto.setEndDate(endDate);
        responseDto.setStartTime(startTime);
        responseDto.setEndTime(endTime);
        responseDto.setLocation(location);
        responseDto.setDescription(description);
        responseDto.setIsFree(isFree);

        BigDecimal lowestPrice = ticketTypes.stream().map(TicketType::getPrice).min(BigDecimal::compareTo).orElse(null);
        if (lowestPrice != null) {
            responseDto.setLowestPrice(lowestPrice.doubleValue());
        }
        responseDto.setCategory(category.getName());

        OrganizerDto organizerDto = new OrganizerDto();
        organizerDto.setId(organizer.getId());
        organizerDto.setName(organizer.getName());
        organizerDto.setImage(organizer.getImage());
        organizerDto.setAddress(organizer.getAddress());
        responseDto.setOrganizer(organizerDto);

        return responseDto;
    }
}
