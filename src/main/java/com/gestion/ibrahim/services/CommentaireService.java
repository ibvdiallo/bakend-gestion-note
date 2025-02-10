package com.gestion.ibrahim.services;

import java.util.List;
import com.gestion.ibrahim.entite.Commentaire;

public interface CommentaireService {

    // Récupérer la liste des commentaires associés à une note spécifique
    List<Commentaire> getCommentairesByNoteId(Long noteId);
    
    // Créer un nouveau commentaire
    Commentaire createCommentaire(Commentaire commentaire);
    
    // Supprimer un commentaire en fonction de son ID
     boolean deleteCommentaire(Long commentaireId);
    
    // Récupérer tous les commentaires
    List<Commentaire> getAllCommentaires();
    
    void deleteComment(Long commentId);

    Commentaire updateComment(Long commentId, String newContent);
    
}
