package com.example.msutilisateur.service;

import com.example.msutilisateur.dto.LecteurDTO;
import com.example.msutilisateur.dto.UtilisateurDTO;

import java.util.List;

public interface IUtilisateurService {
    LecteurDTO createLecteur(LecteurDTO lecteurDTO);

    List<UtilisateurDTO> getAllUtilisateurs();

    UtilisateurDTO getUtilisateur(Long id);

    void deleteUtilisateur(Long id);
}
