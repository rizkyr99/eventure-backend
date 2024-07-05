package com.ramarizdev.eventureBackend.voucher.service.impl;

import com.ramarizdev.eventureBackend.event.entity.Event;
import com.ramarizdev.eventureBackend.event.repository.EventRepository;
import com.ramarizdev.eventureBackend.voucher.dto.VoucherDto;
import com.ramarizdev.eventureBackend.voucher.entity.Voucher;
import com.ramarizdev.eventureBackend.voucher.entity.VoucherType;
import com.ramarizdev.eventureBackend.voucher.repository.VoucherRepository;
import com.ramarizdev.eventureBackend.voucher.service.VoucherService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;
    private final EventRepository eventRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository, EventRepository eventRepository) {
        this.voucherRepository = voucherRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public Voucher createVoucher(Long eventId, VoucherDto voucherDto) {
        if(voucherDto.getType().equals(VoucherType.REFERRAL)) {
            voucherDto.setAmount(10);
        }

        Voucher voucher = voucherDto.toEntity();

        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event not found"));

        voucher.setEvent(event);

        return voucherRepository.save(voucher);
    }
}
