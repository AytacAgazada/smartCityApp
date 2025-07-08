package com.example.smartcityapp.controller;

import com.example.smartcityapp.model.entity.CityUser;
import com.example.smartcityapp.service.CityUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class CityUserController {

    private final CityUserService cityUserService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CityUser>> getAllUsers() {
        return ResponseEntity.ok(cityUserService.getAllUsers());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CityUser> addUser(@Valid @RequestBody CityUser cityUser) {
        return ResponseEntity.ok(cityUserService.addUser(cityUser));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CityUser> updateUser(@PathVariable Long id, @Valid @RequestBody CityUser cityUser) {
        return cityUserService.updateUser(id, cityUser)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CityUser> getUserById(@PathVariable Long id) {
        return cityUserService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (cityUserService.getUserById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        cityUserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


}

