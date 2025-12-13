package com.example.msutilisateur.mapper;

import com.example.msutilisateur.dto.LecteurDTO;
import com.example.msutilisateur.dto.UtilisateurDTO;
import com.example.msutilisateur.model.Lecteur;
import com.example.msutilisateur.model.Utilisateur;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UtilisateurMapper {

    UtilisateurDTO toDto(Utilisateur utilisateur);

    LecteurDTO toLecteurDto(Lecteur lecteur);

    Lecteur toLecteur(LecteurDTO dto);

    List<UtilisateurDTO> toDtoList(List<Utilisateur> utilisateurs);
}