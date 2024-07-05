package com.ramarizdev.eventureBackend.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@Table(name = "referral_codes")
public class ReferralCode {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "referral_code_id_gen")
    @SequenceGenerator(name = "referral_code_id_gen", sequenceName = "referral_code_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @NotBlank
    @Column(name = "code", nullable = false)
    private String code;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendee_id", unique = true)
    private Attendee attendee;

    @OneToMany(mappedBy = "referralCode")
    private List<ReferralUsage> referralUsages = new ArrayList<>();

}
