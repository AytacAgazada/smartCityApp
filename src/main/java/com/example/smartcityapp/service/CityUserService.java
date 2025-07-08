package com.example.smartcityapp.service;

import com.example.smartcityapp.model.entity.CityUser;
import com.example.smartcityapp.repository.CityUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityUserService {

    private final CityUserRepository cityUserRepository;
    private final PasswordEncoder passwordEncoder;

    public List<CityUser> getAllUsers() {
        return cityUserRepository.findAll();
    }

    public Optional<CityUser> getUserById(Long id) {
        return cityUserRepository.findById(id);
    }

    public Optional<CityUser> getUserByUsername(String username) {
        return cityUserRepository.findByUserName(username);
    }

    public CityUser addUser(CityUser cityUser) {
        cityUser.setPassword(passwordEncoder.encode(cityUser.getPassword()));
        return cityUserRepository.save(cityUser);
    }

    public Optional<CityUser> updateUser(Long id, CityUser updatedUser) {
        return cityUserRepository.findById(id).map(existingUser -> {
            existingUser.setUserName(updatedUser.getUserName());
            existingUser.setEmail(updatedUser.getEmail());
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
                existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }
            existingUser.setRoles(updatedUser.getRoles());
            return cityUserRepository.save(existingUser);
        });
    }

    public void deleteUser(Long id) {
        cityUserRepository.deleteById(id);
    }
}
