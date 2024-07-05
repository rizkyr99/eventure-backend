package com.ramarizdev.eventureBackend.voucher.controller;

import com.ramarizdev.eventureBackend.response.Response;
import com.ramarizdev.eventureBackend.voucher.dto.VoucherDto;
import com.ramarizdev.eventureBackend.voucher.entity.Voucher;
import com.ramarizdev.eventureBackend.voucher.service.VoucherService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events/{eventId}/vouchers")
@Validated
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping()
    public ResponseEntity<Response<List<Voucher>>> getAllVouchers(@PathVariable Long eventId) {
        List<Voucher> vouchers = voucherService.getAllVouchers(eventId);
        return Response.success("List of vouchers fetched", vouchers);
    }

    @PostMapping()
    public Voucher createVoucher(@Valid @PathVariable Long eventId, @RequestBody VoucherDto voucherDto) {
        return voucherService.createVoucher(eventId, voucherDto);
    }
}
