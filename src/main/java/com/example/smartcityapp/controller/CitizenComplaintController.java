package com.example.smartcityapp.controller;

import com.example.smartcityapp.model.dto.CitizenComplaintDto;
import com.example.smartcityapp.model.entity.CitizenComplaintEntity;
import com.example.smartcityapp.service.CitizenComplaintService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/complaints")
public class CitizenComplaintController {

    private final CitizenComplaintService citizenComplaintService;

    public CitizenComplaintController(CitizenComplaintService citizenComplaintService) {
        this.citizenComplaintService = citizenComplaintService;
    }

    @PostMapping("/create")
    public ResponseEntity<CitizenComplaintEntity> createComplaint(@Valid @RequestBody CitizenComplaintDto citizenComplaintDto) {
        return ResponseEntity.ok(citizenComplaintService.createComplaint(citizenComplaintDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitizenComplaintEntity> getById(@PathVariable Long id) {
        return ResponseEntity.ok(citizenComplaintService.getComplaintById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitizenComplaintEntity> update(@PathVariable Long id, @RequestParam String status, @RequestParam Integer priority) {
        return ResponseEntity.ok(citizenComplaintService.updateComplaint(id, status, priority));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        citizenComplaintService.deleteComplaintById(id);
        return ResponseEntity.noContent().build();
    }

}
