package com.example.msemprunt.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LivreDTO {
    private Long livreId;
    private String titre;
    private String auteur;
    private String isbn;
    private String description;
    private String image;
    private LocalDate datePublication;
    private int nombreCopies;
    private LocalDate dateAcquisition;
    private String category;
}
