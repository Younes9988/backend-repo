package com.example.mslivre.model;

import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Livre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long livreId;

    private String titre;
    private String auteur;

    @Column(unique = true)
    private String isbn;

    private String description;
    private String image;

    private LocalDate datePublication;

    private int nombreCopies;

    private LocalDate dateAcquisition;
    @Enumerated(EnumType.STRING)
    private Category category;
}
