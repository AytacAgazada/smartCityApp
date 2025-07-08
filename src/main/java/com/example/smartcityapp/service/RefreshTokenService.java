package com.example.smartcityapp.service;

import com.example.smartcityapp.model.entity.CityUser;
import com.example.smartcityapp.model.entity.RefreshToken;
import com.example.smartcityapp.repository.RefreshTokenRepository;
import com.example.smartcityapp.repository.CityUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final CityUserRepository cityUserRepository;

    @Value("${jwt.refreshExpirationMs:86400000}") // default 24 saat
    private Long refreshTokenDurationMs;

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        CityUser user = cityUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public boolean isTokenExpired(RefreshToken token) {
        return token.getExpiryDate().isBefore(Instant.now());
    }

    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));
    }

    public void deleteByUserId(Long userId) {
        CityUser user = cityUserRepository.findById(userId).orElseThrow();
        refreshTokenRepository.deleteAll(refreshTokenRepository.findAll().stream()
                .filter(rt -> rt.getUser().equals(user)).toList());
    }
}
