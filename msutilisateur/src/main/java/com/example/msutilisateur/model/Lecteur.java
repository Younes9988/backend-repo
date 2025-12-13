package com.example.msutilisateur.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("LECTEUR")
@Getter
@Setter
@NoArgsConstructor
public class Lecteur extends Utilisateur {


}
