package com.findmypro.repository;
import com.findmypro.model.Category;
import com.findmypro.model.Area;
import com.findmypro.model.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {
    ServiceProvider findByEmailAndPassword(String email, String password);

    List<ServiceProvider> findByCategoryIdAndAreaId(Long categoryId, Long areaId);
    List<ServiceProvider> findByAreaAndCategory(Area area, Category category);
List<ServiceProvider> findByArea_NameAndCategory_Name(String areaName, String categoryName);

}
