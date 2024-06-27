package com.ramarizdev.eventureBackend.category.dto;

import com.ramarizdev.eventureBackend.category.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryRequestDto {
    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String slug;

    public Category toEntity() {
        Category category = new Category();
        category.setName(name);
        category.setSlug(slug);

        return category;
    }
}
