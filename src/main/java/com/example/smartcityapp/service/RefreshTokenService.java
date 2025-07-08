package com.example.smartcityapp.service;

import com.example.smartcityapp.model.entity.CityUser;
import com.example.smartcityapp.model.entity.RefreshToken;
import com.example.smartcityapp.repository.RefreshTokenRepository;
import com.example.smartcityapp.repository.CityUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final CityUserRepository cityUserRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, CityUserRepository cityUserRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.cityUserRepository = cityUserRepository;
    }

    public RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(cityUserRepository.findByUserName(username).get())
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(60000))//10
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));
    }


    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }


    public void deleteByUserId(Long userId) {
        CityUser user = cityUserRepository.findById(userId).orElseThrow();
        refreshTokenRepository.deleteAll(refreshTokenRepository.findAll().stream()
                .filter(rt -> rt.getUser().equals(user)).toList());
    }
}
