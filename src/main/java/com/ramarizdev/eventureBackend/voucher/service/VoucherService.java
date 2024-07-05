package com.ramarizdev.eventureBackend.voucher.service;

import com.ramarizdev.eventureBackend.voucher.dto.VoucherDto;
import com.ramarizdev.eventureBackend.voucher.entity.Voucher;

import java.util.List;

public interface VoucherService {
    Voucher createVoucher(Long eventId, VoucherDto voucherDto);
    List<Voucher> getAllVouchers(Long eventId);
}
