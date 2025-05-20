package com.findmypro.controller;

import com.findmypro.model.ServiceProvider;
import com.findmypro.repository.ServiceProviderRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/providers")
public class ProviderController {
    private final ServiceProviderRepository providerRepository;

    public ProviderController(ServiceProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    @GetMapping("/")
    public List<ServiceProvider> getAllProviders() {
        return providerRepository.findAll();
    }
}