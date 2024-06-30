package com.ramarizdev.eventureBackend.category.service;

import com.ramarizdev.eventureBackend.category.dto.CategoryRequestDto;
import com.ramarizdev.eventureBackend.category.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category createCategory(CategoryRequestDto requestDto);
}
