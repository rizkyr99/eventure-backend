package com.ramarizdev.eventureBackend.user.repository;

import com.ramarizdev.eventureBackend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
