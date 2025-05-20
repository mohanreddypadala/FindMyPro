package com.findmypro.repository;

import com.findmypro.model.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {
    ServiceProvider findByEmailAndPassword(String email, String password);
}
