package com.gestion.ibrahim.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime; 
import com.gestion.ibrahim.entite.Commentaire;
import com.gestion.ibrahim.entite.Note;
import com.gestion.ibrahim.entite.Utilisateur;
import com.gestion.ibrahim.repos.repoCommentaire;
import com.gestion.ibrahim.repos.repoNote;
import com.gestion.ibrahim.repos.repoUtilisateur;

@Service
public class CommentaireImpl implements CommentaireService{
	
	@Autowired
    private repoNote noteRepository;
	 
	@Autowired
	private repoUtilisateur utilisateurRepository;

	
	@Autowired
    private repoCommentaire commentaireRepository;

   
    @Override
    public List<Commentaire> getCommentairesByNoteId(Long noteId) {
        return commentaireRepository.findByNoteId(noteId); // Méthode à créer dans le repository
    }

  
    @Override
    public Commentaire createCommentaire(Commentaire commentaire) {
        // Récupérer l'utilisateur et la note associés (s'ils existent)
        Utilisateur utilisateur = utilisateurRepository.findById(commentaire.getUtilisateur().getId()).orElse(null);
        Note note = noteRepository.findById(commentaire.getNote().getId()).orElse(null);

        if (utilisateur != null && note != null) {
            commentaire.setUtilisateur(utilisateur);
            commentaire.setNote(note);
            commentaire.setDateCreation(LocalDateTime.now()); // Date et heure actuelles
            return commentaireRepository.save(commentaire);
        }
        return null;
    }


    
    public List<Commentaire> getAllCommentaires() {
        return commentaireRepository.findAll();
    }

    public Commentaire getCommentaireById(Long id) {
        Optional<Commentaire> commentaire = commentaireRepository.findById(id);
        return commentaire.orElse(null);
    }

   

    public Commentaire updateCommentaire(Long id, Commentaire commentaire) {
        if (commentaireRepository.existsById(id)) {
            commentaire.setId(id);
            return commentaireRepository.save(commentaire);
        }
        return null;
    }

    public boolean deleteCommentaire(Long id) {
        if (commentaireRepository.existsById(id)) {
            commentaireRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    
    @Override
    public void deleteComment(Long commentId) {
        commentaireRepository.deleteById(commentId);
    }

    @Override
    public Commentaire updateComment(Long commentId, String newContent) {
        Commentaire commentaire = commentaireRepository.findById(commentId)
            .orElseThrow(() -> new IllegalArgumentException("Comment not found with ID: " + commentId));
        commentaire.setContenu(newContent);
        return commentaireRepository.save(commentaire);
    }
}
