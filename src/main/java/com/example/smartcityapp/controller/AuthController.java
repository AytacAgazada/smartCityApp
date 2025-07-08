package com.example.smartcityapp.controller;

import com.example.smartcityapp.model.dto.JwtResponse;
import com.example.smartcityapp.model.dto.LoginRequest;
import com.example.smartcityapp.model.dto.SignupRequest;
import com.example.smartcityapp.model.entity.CityUser;
import com.example.smartcityapp.model.entity.RefreshToken;
import com.example.smartcityapp.service.CityUserService;
import com.example.smartcityapp.service.JwtService;
import com.example.smartcityapp.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CityUserService cityUserService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest request) {

        if (cityUserService.getUserByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already taken");
        }

        CityUser newUser = new CityUser();
        newUser.setUserName(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRoles(request.getRole());

        cityUserService.addUser(newUser);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        System.out.println("Login attempt: " + request.getIdentifier());

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getIdentifier(), request.getPassword()));

        User userDetails = (User) authentication.getPrincipal();

        System.out.println("Authentication successful for user: " + userDetails.getUsername());

        String accessToken = jwtService.generateToken(userDetails.getUsername());

        CityUser user = cityUserService.getUserByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken.getToken()));
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<JwtResponse> refreshToken(@RequestParam String refreshToken) {

        RefreshToken token = refreshTokenService.findByToken(refreshToken);

        if (refreshTokenService.isTokenExpired(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token expired");
        }

        String username = token.getUser().getUserName();

        String newAccessToken = jwtService.generateToken(username);

        return ResponseEntity.ok(new JwtResponse(newAccessToken, refreshToken));
    }
}
