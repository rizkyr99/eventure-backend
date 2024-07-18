package com.ramarizdev.eventureBackend.voucher.controller;

import com.ramarizdev.eventureBackend.response.Response;
import com.ramarizdev.eventureBackend.voucher.dto.CreateVoucherDto;
import com.ramarizdev.eventureBackend.voucher.entity.Voucher;
import com.ramarizdev.eventureBackend.voucher.service.VoucherService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vouchers")
@Validated
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping()
    public ResponseEntity<Response<CreateVoucherDto>> createVoucher(@Valid @RequestBody CreateVoucherDto voucherDto) {
        CreateVoucherDto voucher = voucherService.createVoucher(voucherDto);
        return Response.success("Voucher created successfully", voucher);
    }
}
