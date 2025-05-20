package com.findmypro.repository;

import com.findmypro.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Find all categories linked to an area by area id
    List<Category> findByAreaId(Long areaId);

    // Optional: find by category name ignoring case (useful for your unique check)
    Optional<Category> findByNameIgnoreCase(String name);
}
