package com.findmypro.controller;

import com.findmypro.model.Area;
import com.findmypro.model.Category;
import com.findmypro.model.User;
import com.findmypro.repository.AreaRepository;
import com.findmypro.repository.CategoryRepository;
import com.findmypro.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/login")
    public String showLoginPage() {
        return "user/user-login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            HttpSession session) {
        User user = userRepository.findByEmailAndPassword(email, password);
        if (user != null) {
            session.setAttribute("loggedInUser", user);
            return "redirect:/user/area-selection";
        } else {
            return "redirect:/user/login?error";
        }
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "user/user-register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        userRepository.save(user);
        return "redirect:/user/login";
    }

    @GetMapping("/area-selection")
    public String showAreaSelection(Model model) {
        List<Area> areas = areaRepository.findAll();
        model.addAttribute("areas", areas);
        return "user/area-selection";
    }

    @PostMapping("/select-area")
    public String selectArea(@RequestParam Long areaId, HttpSession session) {
        session.setAttribute("selectedArea", areaId);
        return "redirect:/user/menu";
    }

    @GetMapping("/menu")
    public String showMenuPage(Model model, HttpSession session) {
        Long areaId = (Long) session.getAttribute("selectedArea");
        User user = (User) session.getAttribute("loggedInUser");

        if (areaId == null || user == null) {
            return "redirect:/user/login";
        }

        model.addAttribute("user", user);

        List<Category> categories = categoryRepository.findByAreaId(areaId);
        model.addAttribute("categories", categories);

        return "user/menu";
    }

    @GetMapping("/profile")
    public String showProfilePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/user/login";
        }
        model.addAttribute("user", user);
        return "user/profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute User updatedUser, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/user/login";
        }
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        userRepository.save(user);
        session.setAttribute("loggedInUser", user);
        return "redirect:/user/profile";
    }
}
