package com.example.msauth.web;


import com.example.msauth.client.UtilisateurClient;
import com.example.msauth.domain.AuthUser;
import com.example.msauth.domain.Role;
import com.example.msauth.dto.LoginRequest;
import com.example.msauth.dto.LoginResponse;
import com.example.msauth.dto.RegisterRequest;
import com.example.msauth.dto.UtilisateurCreateRequest;
import com.example.msauth.repository.AuthUserRepository;
import com.example.msauth.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthUserRepository repository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UtilisateurClient utilisateurClient;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        AuthUser user = repository.findByEmail(request.getEmail()).orElseThrow();

        String token = jwtService.generateToken(
                user.getEmail(),
                Map.of("role", user.getRole().name())
        );

        return new LoginResponse(
                token,
                "Bearer",
                user.getId(),
                user.getEmail(),
                user.getRole().name()
        );
    }
    @PostMapping("/register")
    public void register(@RequestBody @Valid RegisterRequest request) {

        if (repository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // 1️⃣ Create auth user
        AuthUser user = new AuthUser();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.LECTEUR);
        user.setEnabled(true);

        AuthUser savedUser = repository.save(user);

        try {
            // 2️⃣ Create utilisateur (business)
            UtilisateurCreateRequest utilisateurRequest = new UtilisateurCreateRequest();
            utilisateurRequest.setEmail(request.getEmail());
            utilisateurRequest.setNom(request.getNom());
            utilisateurRequest.setPrenom(request.getPrenom());
            utilisateurRequest.setTelephone(request.getTelephone()); // or from request later
            utilisateurRequest.setAdresse(request.getAdresse());
            utilisateurRequest.setDateInscription(LocalDate.now());
            utilisateurRequest.setActif(true);
            utilisateurClient.createLecteur(utilisateurRequest);

        } catch (Exception e) {
            // 3️⃣ Rollback auth user if msutilisateur fails
            repository.delete(savedUser);
            throw new RuntimeException("User creation failed in msutilisateur");
        }
    }


}