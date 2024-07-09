package com.ramarizdev.eventureBackend.event.entity;

import com.ramarizdev.eventureBackend.event.dto.ReviewDto;
import com.ramarizdev.eventureBackend.user.entity.Attendee;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_id_gen")
    @SequenceGenerator(name = "review_id_gen", sequenceName = "review_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendee_id", nullable = false)
    private Attendee attendee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }

    public ReviewDto toDto() {
        ReviewDto reviewDto = new ReviewDto();

        reviewDto.setId(id);
        reviewDto.setAttendeeId(attendee.getId());
        reviewDto.setRating(rating);
        reviewDto.setContent(content);
        reviewDto.setEventId(event.getId());

        return reviewDto;
    }
}
