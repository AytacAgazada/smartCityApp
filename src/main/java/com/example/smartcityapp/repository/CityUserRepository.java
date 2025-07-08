package com.example.smartcityapp.repository;

import com.example.smartcityapp.model.entity.CityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityUserRepository extends JpaRepository<CityUser, Long> {

    Optional<CityUser> findByUserName(String userName);
    Optional<CityUser> findByEmail(String email);
}
