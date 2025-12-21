package com.example.msutilisateur.service;

import com.example.msutilisateur.dao.UtilisateurRepository;
import com.example.msutilisateur.dto.LecteurDTO;
import com.example.msutilisateur.dto.UtilisateurDTO;
import com.example.msutilisateur.mapper.UtilisateurMapper;
import com.example.msutilisateur.model.Lecteur;
import com.example.msutilisateur.model.Utilisateur;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UtilisateurServiceImpl implements IUtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    private final UtilisateurMapper mapper;

    @Override
    public LecteurDTO createLecteur(LecteurDTO dto) {

        // Check unique email
        if (utilisateurRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email déjà utilisé");
        }

        // DTO → entity
        Lecteur lecteur = mapper.toLecteur(dto);

        // Encode password

        lecteur.setDateInscription(LocalDate.now());
        lecteur.setActif(true);

        utilisateurRepository.save(lecteur);

        // Entity → DTO
        return mapper.toLecteurDto(lecteur);
    }

    @Override
    public List<UtilisateurDTO> getAllUtilisateurs() {
        return mapper.toDtoList(utilisateurRepository.findAll());
    }

    @Override
    public UtilisateurDTO getUtilisateur(Long id) {

        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        return mapper.toDto(utilisateur);
    }

    @Override
    public void deleteUtilisateur(Long id) {
        utilisateurRepository.deleteById(id);
    }
}

