package com.gestion.ibrahim.repos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

import com.gestion.ibrahim.entite.Commentaire;
import com.gestion.ibrahim.entite.Note;

@CrossOrigin
public interface repoCommentaire extends JpaRepository<Commentaire, Long> {

    // Vous pouvez ajouter d'autres méthodes de recherche si nécessaire, par exemple :
     List<Commentaire> findByNoteId(Long noteId);
     //List<Commentaire> findByUtilisateurId(Long utilisateurId);
     List<Commentaire> findByUtilisateurId(Long utilisateurId); 
     
     
}