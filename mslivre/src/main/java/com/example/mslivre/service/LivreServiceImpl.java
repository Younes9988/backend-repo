package com.example.mslivre.service;

import com.example.mslivre.dao.LivreRepository;
import com.example.mslivre.dto.LivreDTO;
import com.example.mslivre.mapper.LivreMapper;
import com.example.mslivre.model.Livre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LivreServiceImpl implements ILivreService {

    private final LivreRepository livreRepository;
    private final LivreMapper livreMapper;

    @Override
    public List<LivreDTO> findAllLivre() {
        // Pure catalog listing
        return livreRepository.findAll().stream()
                .map(livreMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<LivreDTO> findLivreById(Long id) {
        // Fetch book details without calculating availability
        return livreRepository.findById(id)
                .map(livreMapper::fromEntity);
    }

    @Override
    public LivreDTO addLivre(LivreDTO livreDto) {
        // RG11: Internal check - Ensure ISBN is unique in the catalog [cite: 246]
        if (livreRepository.findByIsbn(livreDto.getIsbn()).isPresent()) {
            throw new IllegalStateException("Un livre avec cet ISBN existe déjà.");
        }

        Livre livre = livreMapper.toEntity(livreDto);

        // RG10: Internal check - Mandatory fields [cite: 244]
        if (livre.getTitre() == null || livre.getAuteur() == null || livre.getIsbn() == null || livre.getCategory() == null) {
            throw new IllegalArgumentException("Titre, Auteur, ISBN et Catégorie sont obligatoires.");
        }

        // We only manage total copies now
        Livre savedLivre = livreRepository.save(livre);
        return livreMapper.fromEntity(savedLivre);
    }

    @Override
    @Transactional
    public LivreDTO updateLivre(LivreDTO livreDto, Long id) {
        Livre existingLivre = livreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livre introuvable avec l'ID : " + id));

        // Update basic information
        existingLivre.setTitre(livreDto.getTitre());
        existingLivre.setAuteur(livreDto.getAuteur());
        existingLivre.setDescription(livreDto.getDescription());
        existingLivre.setImage(livreDto.getImage());
        existingLivre.setCategory(livreDto.getCategory());
        existingLivre.setDatePublication(livreDto.getDatePublication());
        existingLivre.setDateAcquisition(livreDto.getDateAcquisition());

        // Update stock quantity only
        // Since we don't have 'copiesDisponibles' or connection to borrows,
        // we strictly update the total inventory count.
        existingLivre.setNombreCopies(livreDto.getNombreCopies());

        Livre savedLivre = livreRepository.save(existingLivre);
        return livreMapper.fromEntity(savedLivre);
    }

    @Override
    public void deleteLivre(Long id) {
        if (!livreRepository.existsById(id)) {
            throw new RuntimeException("Livre introuvable");
        }

        // RG08 (Check if borrowed) is removed because we cannot check borrowing status here.
        // We assume the validation happens in the frontend or an orchestration layer.
        livreRepository.deleteById(id);
    }
}