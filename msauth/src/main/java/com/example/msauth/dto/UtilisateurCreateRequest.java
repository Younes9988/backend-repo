package com.example.msauth.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class UtilisateurCreateRequest {

    private String nom;
    private String prenom;
    private String email;

    private String telephone;
    private String adresse;

    // REQUIRED FIELDS
    private LocalDate dateInscription;
    private Boolean actif;
}