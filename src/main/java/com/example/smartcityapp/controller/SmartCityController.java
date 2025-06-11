package com.example.smartcityapp.controller;


import com.example.smartcityapp.exception.ResourceNoFoundException;
import com.example.smartcityapp.model.entity.SmartCityRequest;
import com.example.smartcityapp.service.SmartCityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/requests/")
public class SmartCityController {

    private final SmartCityService service;

    public SmartCityController(SmartCityService service) {
        this.service = service;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<SmartCityRequest>> findAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<SmartCityRequest> findById(@PathVariable Long id) {
//        return ResponseEntity.ok(service.findById(id));
        SmartCityRequest serviceRequest = service.findById(id);
        if (serviceRequest != null) {
            return ResponseEntity.ok(serviceRequest);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/get/title")
    public ResponseEntity<SmartCityRequest> findByTitle(@RequestParam String title) {
        SmartCityRequest byIdAndTitle = service.findByTitle(title);
        if (byIdAndTitle == null) {
            throw new ResourceNoFoundException("current resource not found with title: " + title);
        }
        return ResponseEntity.ok(byIdAndTitle);
    }

    @GetMapping("/getAllByTitle")
    public ResponseEntity<List<SmartCityRequest>> findAllByTitle(@RequestParam String title) {
        return ResponseEntity.ok(service.findAllByTitle(title));
    }

    @GetMapping("/get/{id}/title")
    public ResponseEntity<SmartCityRequest> findByIdAndTitle(@PathVariable Long id, @RequestParam String title) {
        SmartCityRequest byIdAndTitle = service.findByIdAndTitle(id, title);
        if (byIdAndTitle == null) {
            throw new ResourceNoFoundException("current resource not found with id: " + id);
        }
        return ResponseEntity.ok(byIdAndTitle);
    }


    @PostMapping("/create")
    public ResponseEntity<SmartCityRequest> createService(@RequestBody SmartCityRequest smartCityRequest) {
        return ResponseEntity.ok(service.createService(smartCityRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SmartCityRequest> updateService(@PathVariable Long id,
                                                        @RequestBody SmartCityRequest smartCityRequest) {
        return ResponseEntity.ok(service.updateService(id, smartCityRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteService(@PathVariable Long id) {
        service.deleteService(id);
        return ResponseEntity.ok("Successfully deleted");
    }
}