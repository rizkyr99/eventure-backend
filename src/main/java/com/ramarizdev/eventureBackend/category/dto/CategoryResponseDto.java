package com.ramarizdev.eventureBackend.category.dto;

import lombok.Data;

@Data
public class CategoryResponseDto {
    private Long id;
    private String name;
    private String slug;
}
