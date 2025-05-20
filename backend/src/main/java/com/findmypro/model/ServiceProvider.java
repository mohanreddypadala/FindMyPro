package com.findmypro.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "service_providers")
public class ServiceProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    private String serviceType;
    private String description;
    private String location;
    private String priceRange;
    private String availability;
}