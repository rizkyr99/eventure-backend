package com.ramarizdev.eventureBackend.voucher.service;

import com.ramarizdev.eventureBackend.voucher.dto.VoucherDto;
import com.ramarizdev.eventureBackend.voucher.entity.Voucher;

public interface VoucherService {
    Voucher createVoucher(Long eventId, VoucherDto voucherDto);
}
