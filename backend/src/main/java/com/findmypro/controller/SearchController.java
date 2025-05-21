package com.findmypro.controller;

import com.findmypro.model.Area;
import com.findmypro.model.Category;
import com.findmypro.model.ServiceProvider;
import com.findmypro.repository.AreaRepository;
import com.findmypro.repository.CategoryRepository;
import com.findmypro.repository.ServiceProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AreaRepository areaRepository;

    @GetMapping("/menu")
    public String showMenu(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("areas", areaRepository.findAll());
        return "user/menu";
    }

    @GetMapping("/professionals")
    public String searchProfessionals(@RequestParam("city") Long cityId,
                                      @RequestParam("category") Long categoryId,
                                      Model model) {

        Optional<Area> areaOpt = areaRepository.findById(cityId);
        Optional<Category> categoryOpt = categoryRepository.findById(categoryId);

        if (areaOpt.isEmpty() || categoryOpt.isEmpty()) {
            model.addAttribute("providers", List.of());
            model.addAttribute("city", "Unknown Area");
            model.addAttribute("categoryName", categoryOpt.map(Category::getName).orElse("Unknown Category"));
            return "user/professionals-list";
        }

        Area area = areaOpt.get();
        Category category = categoryOpt.get();

        List<ServiceProvider> providers = serviceProviderRepository.findByCategoryIdAndAreaId(category.getId(), area.getId());

        model.addAttribute("providers", providers);
        model.addAttribute("city", area.getName());
        model.addAttribute("categoryName", category.getName());

        return "user/professionals-list";
    }
}
