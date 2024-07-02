package com.ramarizdev.eventureBackend.event.specification;

import com.ramarizdev.eventureBackend.category.entity.Category;
import com.ramarizdev.eventureBackend.event.entity.Event;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class EventSpecification {
    public static Specification<Event> hasCategory(String categorySlug) {
        return ((root, query, criteriaBuilder) -> {
            Join<Event, Category> categoryJoin = root.join("category");
            return criteriaBuilder.equal(categoryJoin.get("slug"), categorySlug);
        });
    }

    public static Specification<Event> hasLocation(String location) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("location"), location);
    }

    public static Specification<Event> isFree(boolean isFree) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isFree"), isFree);
    }

    public static Specification<Event> containsKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            String likePattern = "%" + keyword.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), likePattern)
            );
        };
    }
}
