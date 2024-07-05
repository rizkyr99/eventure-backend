package com.ramarizdev.eventureBackend.voucher.dto;

import com.ramarizdev.eventureBackend.voucher.entity.Voucher;
import com.ramarizdev.eventureBackend.voucher.entity.VoucherType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VoucherDto {
    private Long id;

    @NotNull
    private VoucherType type;

    @NotNull
    private Integer amount;

    @NotNull
    private LocalDate expirationDate;

    @NotNull
    private Integer maxUses;

    @NotNull
    private Integer currentUses;

    public Voucher toEntity() {
        Voucher voucher = new Voucher();

        voucher.setType(type);
        voucher.setAmount(amount);
        voucher.setExpirationDate(expirationDate);
        voucher.setMaxUses(maxUses);
        voucher.setCurrentUses(currentUses);

        return voucher;
    }
}
