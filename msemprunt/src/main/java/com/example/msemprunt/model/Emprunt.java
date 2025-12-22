package com.example.msemprunt.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Emprunt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empruntId;

    private Long lecteurId;
    private Long livreId;

    private LocalDate dateEmprunt;
    private LocalDate dateRetourPrevue;
    private LocalDate dateRetourEffective;

    @Enumerated(EnumType.STRING)
    private StatutEmprunt statut;

    private double penalite;
    private Boolean rappelJ1Envoye = false;
    private LocalDate dateRappelJ1;

    // Penalty tracking
    private LocalDate dateDernierePenalite;

    // Overdue notification
    private Boolean notificationRetardEnvoyee = false;
}
