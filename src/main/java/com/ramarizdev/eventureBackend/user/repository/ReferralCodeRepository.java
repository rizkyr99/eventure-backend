package com.ramarizdev.eventureBackend.user.repository;

import com.ramarizdev.eventureBackend.user.entity.ReferralCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReferralCodeRepository extends JpaRepository<ReferralCode, Long> {
}
