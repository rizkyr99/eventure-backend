package com.ramarizdev.eventureBackend.category.service;

import com.ramarizdev.eventureBackend.category.dto.CategoryRequestDto;
import com.ramarizdev.eventureBackend.category.entity.Category;

public interface CategoryService {
    Category createCategory(CategoryRequestDto requestDto);
}
