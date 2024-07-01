package com.ramarizdev.eventureBackend.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Data
@Getter
@Setter
@Table(name = "points")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "point_id_gen")
    @SequenceGenerator(name = "point_id_gen", sequenceName = "point_id_seq")
    @Column(name = "id")
    private Long id;

    @NotNull
    @NotBlank
    @Column(name = "amount", nullable = false)
    private Double amount;

    @NotNull
    @NotBlank
    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @NotNull
    @NotBlank
    @Column(name = "expired_at")
    private Instant expiredAt;

    @ManyToOne
    @JoinColumn(name = "attendee_id", nullable = false)
    private Attendee attendee;
}
