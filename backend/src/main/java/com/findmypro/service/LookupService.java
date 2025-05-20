package com.findmypro.service;

import com.findmypro.model.Area;
import com.findmypro.model.Category;
import com.findmypro.repository.AreaRepository;
import com.findmypro.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LookupService {

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Area> getAllAreas() {
        return areaRepository.findAll();
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
