package com.example.smartcityapp.controller;

import com.example.smartcityapp.model.dto.AuthRequest;
import com.example.smartcityapp.model.dto.JwtResponse;
import com.example.smartcityapp.model.dto.RefreshTokenRequest;
import com.example.smartcityapp.model.entity.CityUser;
import com.example.smartcityapp.model.entity.RefreshToken;
import com.example.smartcityapp.service.CityUserService;
import com.example.smartcityapp.service.JwtService;
import com.example.smartcityapp.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CityUserService cityUserService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<CityUser> signup(@Valid @RequestBody CityUser cityUser) {
        return ResponseEntity.ok(cityUserService.addUser(cityUser));
    }


    @PostMapping("/login")
    public JwtResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getUsername());
            return JwtResponse.builder()
                    .accessToken(jwtService.generateToken(authRequest.getUsername()))
                    .token(refreshToken.getToken())
                    .build();
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    @PostMapping("/refreshToken")
    public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenRequest.getToken());
        refreshToken = refreshTokenService.verifyExpiration(refreshToken);
        CityUser user = refreshToken.getUser();
        String accessToken = jwtService.generateToken(user.getUserName());

        return JwtResponse.builder()
                .accessToken(accessToken)
                .token(refreshTokenRequest.getToken())
                .build();
    }


}
