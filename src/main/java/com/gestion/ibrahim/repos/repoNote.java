package com.gestion.ibrahim.repos;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.gestion.ibrahim.entite.Note;


@CrossOrigin
public interface repoNote extends JpaRepository<Note, Long> {
    List<Note> findByCours(String cours); 
    // Trouver les notes d'un cours spécifique
    List<Note> findByTitreContainingIgnoreCaseOrContenuContainingIgnoreCaseOrCoursContainingIgnoreCase(String titre, String contenu, String cours);
    
    List<Note> findByAuteurId(Long userLongId);

    // Trouver les notes où l'auteur correspond à un utilisateur spécifique
    //List<Note> findByAuteurId(String auteur);
    
    long countByAuteurId(Long auteurId);
    
    Long countByAuteur_Id(Long id);
    
}
