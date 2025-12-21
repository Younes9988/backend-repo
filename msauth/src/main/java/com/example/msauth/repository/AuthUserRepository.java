package com.example.msauth.repository;


import com.example.msauth.domain.AuthUser;
import org.apache.http.auth.AUTH;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {

    Optional<AuthUser> findByEmail(String email);

    boolean existsByEmail(String email);
}