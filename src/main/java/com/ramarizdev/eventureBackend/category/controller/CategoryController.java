package com.ramarizdev.eventureBackend.category.controller;

import com.ramarizdev.eventureBackend.category.dto.CategoryRequestDto;
import com.ramarizdev.eventureBackend.category.entity.Category;
import com.ramarizdev.eventureBackend.category.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@Validated
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();

        return ResponseEntity.ok().body(categories);
    }

    @PostMapping()
    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequestDto requestDto) {
        Category category = categoryService.createCategory(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }
}
