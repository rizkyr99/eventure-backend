package com.ramarizdev.eventureBackend.order.entity;

import com.ramarizdev.eventureBackend.event.entity.Event;
import com.ramarizdev.eventureBackend.order.dto.OrderDto;
import com.ramarizdev.eventureBackend.order.dto.OrderItemDto;
import com.ramarizdev.eventureBackend.user.entity.Attendee;
import com.ramarizdev.eventureBackend.user.entity.Point;
import com.ramarizdev.eventureBackend.voucher.entity.Voucher;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "order_vouchers",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "voucher_id"))
    private List<Voucher> vouchers = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @NotNull
    @Column(name = "order_date", nullable = false)
    private Instant orderDate;

    @NotNull
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    public OrderDto toDto() {
        OrderDto orderDto = new OrderDto();

        List<OrderItemDto> orderItemDtos = orderItems.stream().map(
                OrderItem::toDto
        ).collect(Collectors.toList());

        orderDto.setId(id);
        orderDto.setEventId(event.getId());
        orderDto.setTotalPrice(totalPrice);
        orderDto.setOrderItems(orderItemDtos);
        orderDto.setAttendeeId(attendee.getId());

        return orderDto;
    }

    public boolean hasReferralVoucher() {
        return vouchers.stream().anyMatch(Voucher::isReferral);
    }

    @PrePersist
    public void prePersist() {
        orderDate = Instant.now();
    }
}
