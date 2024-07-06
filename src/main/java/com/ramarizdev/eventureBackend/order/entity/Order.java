package com.ramarizdev.eventureBackend.order.entity;

import com.ramarizdev.eventureBackend.event.entity.Event;
import com.ramarizdev.eventureBackend.user.entity.Attendee;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_gen")
    @SequenceGenerator(name = "order_id_gen", sequenceName = "order_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "attendee_id", nullable = false)
    private Attendee attendee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @NotNull
    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @NotNull
    @Column(name = "total_price", nullable = false)
    private Double totalPrice;
}
