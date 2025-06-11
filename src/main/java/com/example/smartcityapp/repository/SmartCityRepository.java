package com.example.smartcityapp.repository;

import com.example.smartcityapp.model.entity.SmartCityRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmartCityRepository extends JpaRepository<SmartCityRequest, Long> {
    SmartCityRequest findByTitle(String title);

    List<SmartCityRequest> findAllByTitle(String title);

    SmartCityRequest findByIdAndTitle(Long id, String title);
}
