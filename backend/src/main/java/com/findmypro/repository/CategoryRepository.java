package com.findmypro.repository;

import com.findmypro.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByAreaId(Long areaId);
}
