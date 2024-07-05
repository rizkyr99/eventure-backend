package com.ramarizdev.eventureBackend.user.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@Table(name = "referral_usages")
public class ReferralUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "referral_usage_id_gen")
    @SequenceGenerator(name = "referral_usage_id_gen", sequenceName = "referral_usage_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "referral_code_id", nullable = false)
    private ReferralCode referralCode;

    @OneToOne
    @JoinColumn(name = "attendee_id", nullable = false, unique = true)
    private Attendee referredAttendee;

    @Column(name = "used_at", nullable = false)
    private Instant usedAt;

    @PrePersist
    public void prePersist() {
        usedAt = Instant.now();
    }
}
