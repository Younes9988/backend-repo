package com.example.msemprunt.service;

import com.example.msemprunt.dto.EmpruntDTO;

import java.util.List;

public interface IEmpruntService {
    EmpruntDTO creerEmprunt(Long lecteurId, Long livreId);
    EmpruntDTO retournerEmprunt(Long empruntId);
    List<EmpruntDTO> getEmprunts();
    EmpruntDTO getEmprunt(Long id);
    List<EmpruntDTO> getEmpruntsByLecteur(Long lecteurId);
}

