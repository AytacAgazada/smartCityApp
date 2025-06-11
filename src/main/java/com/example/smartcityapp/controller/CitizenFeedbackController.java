package com.example.smartcityapp.controller;

import com.example.smartcityapp.model.dto.CitizenFeedbackDto;
import com.example.smartcityapp.model.entity.CitizenFeedback;
import com.example.smartcityapp.service.CitizenFeedbackService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CitizenFeedbackController {

    private final CitizenFeedbackService citizenFeedbackService;

    public CitizenFeedbackController(CitizenFeedbackService citizenFeedbackService) {
        this.citizenFeedbackService = citizenFeedbackService;
    }

    @PostMapping("/create")
    public ResponseEntity<CitizenFeedback> createFeedback(@RequestBody @Valid CitizenFeedbackDto citizenFeedbackDto) {
        return ResponseEntity.ok(citizenFeedbackService.create(citizenFeedbackDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitizenFeedback> getById(@PathVariable Long id) {
        return ResponseEntity.ok(citizenFeedbackService.getById(id));
    }
}
