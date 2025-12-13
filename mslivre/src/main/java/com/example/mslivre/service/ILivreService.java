package com.example.mslivre.service;

import com.example.mslivre.dto.LivreDTO;
import com.example.mslivre.model.Livre;

import java.util.List;
import java.util.Optional;

public interface ILivreService {
    List<LivreDTO> findAllLivre();
    Optional<LivreDTO> findLivreById(Long id);
    LivreDTO addLivre(LivreDTO livreDto);
    LivreDTO updateLivre(LivreDTO livreDto, Long id);
    void deleteLivre(Long id);
}
