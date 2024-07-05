package com.ramarizdev.eventureBackend.voucher.controller;

import com.ramarizdev.eventureBackend.voucher.dto.VoucherDto;
import com.ramarizdev.eventureBackend.voucher.entity.Voucher;
import com.ramarizdev.eventureBackend.voucher.service.VoucherService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/events/{eventId}/vouchers")
@Validated
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping()
    public Voucher createVoucher(@Valid @PathVariable Long eventId, @RequestBody VoucherDto voucherDto) {
        return voucherService.createVoucher(eventId, voucherDto);
    }
}
