package com.ramarizdev.eventureBackend.category.service.impl;

import com.ramarizdev.eventureBackend.category.dto.CategoryRequestDto;
import com.ramarizdev.eventureBackend.category.entity.Category;
import com.ramarizdev.eventureBackend.category.repository.CategoryRepository;
import com.ramarizdev.eventureBackend.category.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(CategoryRequestDto requestDto) {
        Category category = requestDto.toEntity();
        Category newCategory = categoryRepository.save(category);
        return newCategory;
    }
}
