package com.findmypro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class GeneralController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/select")
    public String selectRolePage() {
        return "select";
    }
    @GetMapping("/about")
    public String aboutpage() {
        return "about";
    }

    @GetMapping("/contact")
    public String contactpage() {
        return "contact";
    }

    @GetMapping("/privacy")
    public String privacypage() {
        return "privacy";
    }

    @GetMapping("/terms")
    public String termspage() {
        return "terms";
    }

    @GetMapping("/login")
    public String loginpage() {
        return "select";
    }
    
}