package com.gestion.ibrahim.repos;

import com.gestion.ibrahim.entite.Commentaire;
import com.gestion.ibrahim.entite.Utilisateur;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public interface repoUtilisateur  extends JpaRepository<Utilisateur, Long> {
	Utilisateur findByEmail(String email);
    boolean existsByEmail(String email);
    List<Utilisateur> findByNomContainingIgnoreCaseOrEmailContainingIgnoreCase(String nom, String email);

}