package com.findmypro.repository;

import com.findmypro.model.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {
    List<ServiceProvider> findByServiceType(String serviceType);
}