package com.ramarizdev.eventureBackend.voucher.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ramarizdev.eventureBackend.event.entity.Event;
import com.ramarizdev.eventureBackend.voucher.dto.CreateVoucherDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Data
@Getter
@Setter
@Table(name = "vouchers")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "voucher_id_gen")
    @SequenceGenerator(name = "voucher_id_gen", sequenceName = "voucher_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private VoucherType type;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Integer amount;

    @NotNull
    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @NotNull
    @Column(name = "max_uses", nullable = false)
    private Integer maxUses;

    @NotNull
    @Column(name = "current_uses", nullable = false)
    private Integer currentUses;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    public CreateVoucherDto toDto() {
        CreateVoucherDto voucherDto = new CreateVoucherDto();

        voucherDto.setId(id);
        voucherDto.setType(type);
        voucherDto.setAmount(amount);
        voucherDto.setExpirationDate(expirationDate);
        voucherDto.setMaxUses(maxUses);
        voucherDto.setCurrentUses(currentUses);

        return voucherDto;
    }
}
