package com.findmypro.repository;

import com.findmypro.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AreaRepository extends JpaRepository<Area, Long> {
    
    // Add this method declaration
    Optional<Area> findByNameIgnoreCase(String name);
}
