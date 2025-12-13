package com.example.msemprunt.dto;

import com.example.msemprunt.model.StatutEmprunt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpruntDTO {
    private Long id;
    private Long lecteurId;
    private Long livreId;
    private LocalDate dateEmprunt;
    private LocalDate dateRetourPrevue;
    private LocalDate dateRetourEffective;
    private StatutEmprunt statutEmprunt;

    private double penalite;
}

