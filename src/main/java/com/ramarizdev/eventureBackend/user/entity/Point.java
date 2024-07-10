package com.ramarizdev.eventureBackend.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDate;

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
    @Column(name = "amount", nullable = false)
    private Integer amount;

    @NotNull
    @Column(name = "collection_date", nullable = false, updatable = false)
    private LocalDate collectionDate;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attendee_id", nullable = false)
    private Attendee attendee;
}
