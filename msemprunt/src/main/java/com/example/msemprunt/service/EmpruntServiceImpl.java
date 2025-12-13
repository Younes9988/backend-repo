package com.example.msemprunt.service;

import com.example.msemprunt.dao.EmpruntRepository;
import com.example.msemprunt.dto.EmpruntDTO;
import com.example.msemprunt.client.LivreClient;
import com.example.msemprunt.client.UtilisateurClient;
import com.example.msemprunt.model.Emprunt;
import com.example.msemprunt.model.StatutEmprunt;
import com.example.msemprunt.dto.LivreDTO;
import com.example.msemprunt.dto.UtilisateurDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EmpruntServiceImpl implements IEmpruntService {

    // règles que tu peux adapter facilement
    private static final int MAX_EMPRUNTS_PAR_LECTEUR = 3;
    private static final int DUREE_EMPRUNT_JOURS = 15;

    private final EmpruntRepository empruntRepository;
    private final LivreClient livreClient;
    private final UtilisateurClient utilisateurClient;

    @Override
    public EmpruntDTO creerEmprunt(Long lecteurId, Long livreId) {

        // 1. Vérifier lecteur
        UtilisateurDTO lecteur = utilisateurClient.getUtilisateurById(lecteurId);
        if (lecteur == null) {
            throw new RuntimeException("Lecteur introuvable");
        }
        if (Boolean.FALSE.equals(lecteur.getActif())) {
            throw new RuntimeException("Lecteur inactif – emprunt impossible");
        }
        if (lecteur.getRole() == null || !lecteur.getRole().equalsIgnoreCase("LECTEUR")) {
            throw new RuntimeException("Seuls les lecteurs peuvent emprunter des livres");
        }

        // 2. Vérifier livre
        LivreDTO livre = livreClient.getLivreById(livreId);
        if (livre == null) {
            throw new RuntimeException("Livre introuvable");
        }

        // 3. Règle: limite d'emprunts
        long nbEmpruntsLecteur = empruntRepository.countByLecteurIdAndStatut(lecteurId, StatutEmprunt.EN_COURS);
        if (nbEmpruntsLecteur >= MAX_EMPRUNTS_PAR_LECTEUR) {
            throw new RuntimeException("Limite d'emprunts atteinte pour ce lecteur");
        }

        // 4. Vérifier disponibilité réelle (calculée depuis Emprunts)
        long nbEmpruntsLivre = empruntRepository.countByLivreIdAndStatut(livreId, StatutEmprunt.EN_COURS);
        int copiesTotales = livre.getNombreCopies();
        int copiesRestantes = copiesTotales - (int) nbEmpruntsLivre;
        if (copiesRestantes <= 0) {
            throw new RuntimeException("Aucune copie disponible pour ce livre");
        }


        // 5. Créer l’emprunt
        Emprunt emprunt = new Emprunt();
        emprunt.setLecteurId(lecteurId);
        emprunt.setLivreId(livreId);
        emprunt.setDateEmprunt(LocalDate.now());
        emprunt.setDateRetourPrevue(LocalDate.now().plusDays(DUREE_EMPRUNT_JOURS));
        emprunt.setStatut(StatutEmprunt.EN_COURS);

        Emprunt saved = empruntRepository.save(emprunt);
        // 🔥🔥🔥 ADD THIS BLOCK: UPDATE BOOK STOCK IN MS-LIVRE
        livre.setNombreCopies(livre.getNombreCopies() - 1);
        livreClient.updateLivre(livreId, livre);
        return toDTO(saved);
    }


    @Override
    public EmpruntDTO retournerEmprunt(Long empruntId) {

        // 1. Find emprunt
        Emprunt emprunt = empruntRepository.findById(empruntId)
                .orElseThrow(() -> new RuntimeException("Emprunt introuvable"));

        if (emprunt.getStatut() != StatutEmprunt.EN_COURS) {
            throw new RuntimeException("Cet emprunt n'est pas en cours");
        }

        // 2. Fetch the corresponding book
        LivreDTO livre = livreClient.getLivreById(emprunt.getLivreId());
        if (livre == null) {
            throw new RuntimeException("Livre introuvable pour retour d'emprunt");
        }

        // 3. Increase stock
        livre.setNombreCopies(livre.getNombreCopies() + 1);

        // 4. Update book in ms-livre
        livreClient.updateLivre(livre.getLivreId(), livre);

        // --- SET RETURN DATE FIRST ---
        LocalDate now = LocalDate.now();
        emprunt.setDateRetourEffective(now);

        // --- APPLY PENALTY ---
        if (now.isAfter(emprunt.getDateRetourPrevue())) {
            long joursEnRetard = java.time.temporal.ChronoUnit.DAYS
                    .between(emprunt.getDateRetourPrevue(), now);

            double penaliteParJour = 5.0;
            emprunt.setPenalite(joursEnRetard * penaliteParJour);
        } else {
            emprunt.setPenalite(0.0);
        }

        // --- STATUS UPDATE ---
        emprunt.setStatut(StatutEmprunt.RETOURNE);

        Emprunt saved = empruntRepository.save(emprunt);
        return toDTO(saved);
    }


    @Override
    @Transactional(readOnly = true)
    public List<EmpruntDTO> getEmprunts() {
        return empruntRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EmpruntDTO getEmprunt(Long id) {
        Emprunt emprunt = empruntRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Emprunt introuvable"));
        return toDTO(emprunt);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmpruntDTO> getEmpruntsByLecteur(Long lecteurId) {
        return empruntRepository.findByLecteurId(lecteurId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // --------- mapping simple Entity <-> DTO ---------

    private EmpruntDTO toDTO(Emprunt e) {
        EmpruntDTO dto = new EmpruntDTO();
        dto.setId(e.getEmpruntId());
        dto.setLecteurId(e.getLecteurId());
        dto.setLivreId(e.getLivreId());
        dto.setDateEmprunt(e.getDateEmprunt());
        dto.setDateRetourPrevue(e.getDateRetourPrevue());
        dto.setDateRetourEffective(e.getDateRetourEffective());
        dto.setStatutEmprunt(e.getStatut());
        dto.setPenalite(e.getPenalite());
        return dto;
    }
}
