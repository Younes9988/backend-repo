package com.example.msutilisateur.presentation;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RestController;
import com.example.msutilisateur.dto.LecteurDTO;
import com.example.msutilisateur.dto.UtilisateurDTO;
import com.example.msutilisateur.service.IUtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
public class UtilisateurController {

    private final IUtilisateurService utilisateurService;

    // ---------------------------
    // CREATE LECTEUR
    // ---------------------------
    @PostMapping("/lecteur")
    public ResponseEntity<LecteurDTO> createLecteur(@RequestBody @Valid LecteurDTO lecteurDTO) {
        LecteurDTO saved = utilisateurService.createLecteur(lecteurDTO);
        return ResponseEntity.ok(saved);
    }

    // ---------------------------
    // GET ALL USERS
    // ---------------------------
    @GetMapping
    public ResponseEntity<List<UtilisateurDTO>> getAllUsers() {
        return ResponseEntity.ok(utilisateurService.getAllUtilisateurs());
    }
    @GetMapping("/by-email/{email}")
    public UtilisateurDTO getByEmail(@PathVariable String email) {
        return utilisateurService.getByEmail(email);
    }
    // ---------------------------
    // GET ONE USER BY ID
    // ---------------------------
    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(utilisateurService.getUtilisateur(id));
    }

    // ---------------------------
    // DELETE USER
    // ---------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        utilisateurService.deleteUtilisateur(id);
        return ResponseEntity.noContent().build();
    }
}
