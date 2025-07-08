package com.example.smartcityapp.service;

import com.example.smartcityapp.model.entity.CityUser;
import com.example.smartcityapp.repository.CityUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CityUserDetailsService implements UserDetailsService {

    private final CityUserRepository cityUserRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        System.out.println("Loading user by identifier: " + identifier);
        var userOptByUserName = cityUserRepository.findByUserName(identifier);
        var userOptByEmail = cityUserRepository.findByEmail(identifier);

        if(userOptByUserName.isPresent()) {
            System.out.println("Found by username");
            var user = userOptByUserName.get();
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRoles().name()))
            );
        }

        if(userOptByEmail.isPresent()) {
            System.out.println("Found by email");
            var user = userOptByEmail.get();
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRoles().name()))
            );
        }

        throw new UsernameNotFoundException("User not found with identifier: " + identifier);
    }



}
