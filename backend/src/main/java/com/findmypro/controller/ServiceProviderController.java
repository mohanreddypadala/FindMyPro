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

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("provider", new ServiceProvider());
        return "serviceprovider/provider-register";
    }

    @PostMapping("/register")
    public String registerProvider(@ModelAttribute("provider") ServiceProvider provider,
                                   @RequestParam("place") String place,
                                   @RequestParam("categoryName") String categoryName,
                                   @RequestParam("phone") String phone) {

        String trimmedPlace = place.trim();
        String trimmedCategory = categoryName.trim();

        // Find or create area
        Optional<Area> optionalArea = areaRepository.findByNameIgnoreCase(trimmedPlace);
        Area area = optionalArea.orElseGet(() -> {
            Area newArea = new Area();
            newArea.setName(trimmedPlace);
            return areaRepository.save(newArea);
        });
        provider.setArea(area);

        // Find or create category with area
        Optional<Category> optionalCategory =
                categoryRepository.findByNameIgnoreCaseAndAreaId(trimmedCategory, area.getId());

        Category category = optionalCategory.orElseGet(() -> {
            Category newCategory = new Category();
            newCategory.setName(trimmedCategory);
            newCategory.setArea(area);
            return categoryRepository.save(newCategory);
        });

        provider.setCategory(category);
        provider.setPhone(phone);

        serviceProviderRepository.save(provider);

        return "redirect:/provider/login";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "serviceprovider/provider-login";
    }

    @PostMapping("/login")
    public String loginProvider(@RequestParam String email,
                                @RequestParam String password,
                                HttpSession session) {

        ServiceProvider provider = serviceProviderRepository.findByEmailAndPassword(email, password);
        if (provider != null) {
            session.setAttribute("loggedInProvider", provider);
            return "redirect:/provider/dashboard";
        } else {
            return "redirect:/provider/login?error";
        }
    }

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        ServiceProvider provider = (ServiceProvider) session.getAttribute("loggedInProvider");

        if (provider == null) {
            return "redirect:/provider/login";
        }

        Optional<ServiceProvider> optionalProvider = serviceProviderRepository.findById(provider.getId());
        if (optionalProvider.isEmpty()) {
            return "redirect:/provider/login";
        }

        model.addAttribute("provider", provider);
        return "serviceprovider/dashboard";
    }
}
