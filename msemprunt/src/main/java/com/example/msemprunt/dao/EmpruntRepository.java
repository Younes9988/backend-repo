package com.example.msemprunt.dao;

import com.example.msemprunt.model.Emprunt;
import com.example.msemprunt.model.StatutEmprunt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {
    // nombre d’emprunts EN_COURS pour un livre
    long countByLivreIdAndStatut(Long livreId, StatutEmprunt statut);

    // emprunts EN_COURS pour un lecteur
    long countByLecteurIdAndStatut(Long lecteurId, StatutEmprunt statut);

    List<Emprunt> findByLecteurId(Long lecteurId);
    List<Emprunt> findByLivreId(Long livreId);
    List<Emprunt> findByStatutAndDateRetourPrevueAndRappelJ1EnvoyeFalse(
            StatutEmprunt statut,
            LocalDate dateRetourPrevue
    );
    Page<Emprunt> findByStatutAndDateRetourPrevueAndRappelJ1EnvoyeFalse(
            StatutEmprunt statut,
            LocalDate dateRetourPrevue,
            Pageable pageable
    );
    Page<Emprunt> findByStatutAndDateRetourPrevueBefore(
            StatutEmprunt statut,
            LocalDate today,
            Pageable pageable
    );
}
