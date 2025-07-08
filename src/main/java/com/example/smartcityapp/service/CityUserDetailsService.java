package com.example.smartcityapp.service;

import com.example.smartcityapp.model.entity.CityUser;
import com.example.smartcityapp.repository.CityUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class CityUserDetailsService implements UserDetailsService {

    private final CityUserRepository cityUserRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        log.debug("Attempting to load user by identifier: {}", identifier);

        var userOptByUserName = cityUserRepository.findByUserName(identifier);
        if (userOptByUserName.isPresent()) {
            var user = userOptByUserName.get();
            log.info("User found by username: {}", user.getUserName());
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRoles().name()))
            );
        }

        var userOptByEmail = cityUserRepository.findByEmail(identifier);
        if (userOptByEmail.isPresent()) {
            var user = userOptByEmail.get();
            log.info("User found by email: {}", user.getEmail());
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRoles().name()))
            );
        }

        log.warn("User not found with identifier: {}", identifier);
        throw new UsernameNotFoundException("User not found with identifier: " + identifier);
    }



}
