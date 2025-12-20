package com.example.msemprunt.presentation;

import com.example.msemprunt.dto.EmpruntDTO;
import com.example.msemprunt.service.IEmpruntService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emprunts")
@RequiredArgsConstructor
public class EmpruntController {

    private final IEmpruntService empruntService;

    // Créer un emprunt
    @PostMapping
    public ResponseEntity<EmpruntDTO> creerEmprunt(
            @RequestParam Long lecteurId,
            @RequestParam Long livreId
    ) {
        return ResponseEntity.ok(empruntService.creerEmprunt(lecteurId, livreId));
    }

    // Retourner un emprunt
    @PutMapping("/{id}/retour")
    public ResponseEntity<EmpruntDTO> retournerEmprunt(@PathVariable Long id) {
        return ResponseEntity.ok(empruntService.retournerEmprunt(id));
    }

    // Liste de tous les emprunts
    @GetMapping
    public ResponseEntity<List<EmpruntDTO>> getAllEmprunts() {
        return ResponseEntity.ok(empruntService.getEmprunts());
    }

    // Détails d’un emprunt
    @GetMapping("/{id}")
    public ResponseEntity<EmpruntDTO> getEmprunt(@PathVariable Long id) {
        return ResponseEntity.ok(empruntService.getEmprunt(id));
    }

    // Emprunts d’un lecteur
    @GetMapping("/lecteur/{lecteurId}")
    public ResponseEntity<List<EmpruntDTO>> getEmpruntsByLecteur(@PathVariable Long lecteurId) {
        return ResponseEntity.ok(empruntService.getEmpruntsByLecteur(lecteurId));
    }
}
