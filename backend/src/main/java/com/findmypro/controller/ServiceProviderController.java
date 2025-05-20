package com.findmypro.controller;

import com.findmypro.model.Area;
import com.findmypro.model.Category;
import com.findmypro.model.ServiceProvider;
import com.findmypro.repository.AreaRepository;
import com.findmypro.repository.CategoryRepository;
import com.findmypro.repository.ServiceProviderRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/provider")
public class ServiceProviderController {

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Show registration page
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("provider", new ServiceProvider());
        return "serviceprovider/provider-register";  // Make sure template path is correct
    }

    // Handle registration form submission
    @PostMapping("/register")
    public String registerProvider(@ModelAttribute("provider") ServiceProvider provider,
                                   @RequestParam("place") String place,
                                   @RequestParam("categoryName") String categoryName) {

        String trimmedPlace = place.trim();
        String trimmedCategory = categoryName.trim();

        // Find or create Area (place)
        Optional<Area> optionalArea = areaRepository.findByNameIgnoreCase(trimmedPlace);
        Area area = optionalArea.orElseGet(() -> {
            Area newArea = new Area();
            newArea.setName(trimmedPlace);
            return areaRepository.save(newArea);
        });
        provider.setArea(area);

        // Find or create Category (categoryName)
        Optional<Category> optionalCategory = categoryRepository.findByNameIgnoreCase(trimmedCategory);
        Category category = optionalCategory.orElseGet(() -> {
            Category newCategory = new Category();
            newCategory.setName(trimmedCategory);
            return categoryRepository.save(newCategory);
        });
        provider.setCategory(category);

        // Save service provider with linked area and category
        serviceProviderRepository.save(provider);

        // Redirect to login page after successful registration
        return "redirect:/provider/login";
    }

    // Show login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "serviceprovider/provider-login";  // Make sure this template exists
    }

    // Handle login form submission
    @PostMapping("/login")
    public String loginProvider(@RequestParam String email,
                                @RequestParam String password,
                                HttpSession session) {
        ServiceProvider provider = serviceProviderRepository.findByEmailAndPassword(email, password);

        if (provider != null) {
            // Store logged in provider in session
            session.setAttribute("loggedInProvider", provider);
            return "redirect:/provider/dashboard";
        } else {
            // Login failed, redirect back with error flag
            return "redirect:/provider/login?error";
        }
    }

    // Provider dashboard page - requires login session
    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        ServiceProvider provider = (ServiceProvider) session.getAttribute("loggedInProvider");

        if (provider == null) {
            // If not logged in, redirect to login page
            return "redirect:/provider/login";
        }

        Optional<ServiceProvider> optionalProvider = serviceProviderRepository.findById(provider.getId());
        if (optionalProvider.isEmpty()) {
            return "redirect:/provider/login";
        }

        // Pass provider data to dashboard view
        model.addAttribute("provider", provider);
        return "serviceprovider/dashboard";
    }
}
