package com.example.msutilisateur.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("BIBLIOTHECAIRE")
@Getter
@Setter
@NoArgsConstructor
public class Bibliothecaire extends Utilisateur {

}
