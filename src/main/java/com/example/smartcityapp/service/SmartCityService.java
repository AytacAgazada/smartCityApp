package com.example.smartcityapp.service;

import com.example.smartcityapp.exception.ResourceNoFoundException;
import com.example.smartcityapp.model.entity.SmartCityRequest;
import com.example.smartcityapp.repository.SmartCityRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmartCityService {

    private final SmartCityRepository smartCityRepository;

    public SmartCityService(SmartCityRepository smartCityRepository) {
        this.smartCityRepository = smartCityRepository;
    }

    @PostConstruct
    public void initialize() {
        System.out.println("Application started successfully");
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("🔥 TaskService bean is being destroyed. Clearing tasks...");
    }

    public List<SmartCityRequest> getAll() {
        return smartCityRepository.findAll();
    }

    public SmartCityRequest findById(Long id) {
        return smartCityRepository.findById(id)
                .orElseThrow(() -> new ResourceNoFoundException("resource not found with id: " + id));
    }

    public SmartCityRequest findByTitle(String title) {
        return smartCityRepository.findByTitle(title);
    }

    public SmartCityRequest findByIdAndTitle(Long id, String title) {
        return smartCityRepository.findByIdAndTitle(id, title);
    }

    public List<SmartCityRequest> findAllByTitle(String title) {
        return smartCityRepository.findAllByTitle(title);
    }

    public SmartCityRequest createService(SmartCityRequest serviceRequest) {
        return smartCityRepository.save(serviceRequest);
    }

    public SmartCityRequest updateService(Long id, SmartCityRequest serviceRequest) {
        SmartCityRequest service = findById(id);
        service.setDescription(serviceRequest.getDescription());
        service.setStatus(serviceRequest.getStatus());
        service.setTitle(serviceRequest.getTitle());
        return smartCityRepository.save(service);
    }

    public void deleteService(Long id) {
        SmartCityRequest service = findById(id);
        smartCityRepository.delete(service);
    }
}