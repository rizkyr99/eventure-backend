package com.ramarizdev.eventureBackend.category.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ramarizdev.eventureBackend.category.dto.CategoryResponseDto;
import com.ramarizdev.eventureBackend.event.entity.Event;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Data
@Getter
@Setter
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_gen")
    @SequenceGenerator(name = "category_id_gen", sequenceName = "category_id_seq")
    private Long id;

    @NotNull
    @NotBlank
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @NotBlank
    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Event> events;

    public CategoryResponseDto toDto() {
        CategoryResponseDto responseDto = new CategoryResponseDto();

        responseDto.setId(id);
        responseDto.setName(name);
        responseDto.setSlug(slug);

        return responseDto;
    }
}
