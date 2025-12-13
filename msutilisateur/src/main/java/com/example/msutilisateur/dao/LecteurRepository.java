package com.example.msutilisateur.dao;

import com.example.msutilisateur.model.Lecteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LecteurRepository extends JpaRepository<Lecteur, Long> {
}
