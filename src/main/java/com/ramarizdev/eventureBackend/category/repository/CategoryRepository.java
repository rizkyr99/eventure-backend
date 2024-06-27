package com.ramarizdev.eventureBackend.category.repository;

import com.ramarizdev.eventureBackend.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
