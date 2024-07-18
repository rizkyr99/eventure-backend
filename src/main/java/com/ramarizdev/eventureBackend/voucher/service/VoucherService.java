package com.ramarizdev.eventureBackend.voucher.service;

import com.ramarizdev.eventureBackend.voucher.dto.CreateVoucherDto;
import com.ramarizdev.eventureBackend.voucher.entity.Voucher;

import java.util.List;

public interface VoucherService {
    CreateVoucherDto createVoucher(CreateVoucherDto voucherDto);
    Voucher getVoucherById(Long voucherId);
    List<Voucher> getAllVouchers(Long eventId);
}
