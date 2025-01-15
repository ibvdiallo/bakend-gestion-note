package com.gestion.ibrahim.restcontroller;

import com.gestion.ibrahim.entite.Commentaire;
import com.gestion.ibrahim.repos.repoCommentaire;
import com.gestion.ibrahim.services.CommentaireService;
import com.gestion.ibrahim.services.NoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/commentaires")
public class CommentaireController {

    @Autowired
    private CommentaireService commentaireService;
    
    @Autowired
    private NoteService noteService;
    
	@Autowired
    private repoCommentaire commentaireRepository;
	
	
	@GetMapping("/note/{noteId}")
	public ResponseEntity<List<Commentaire>> getCommentairesByNote(@PathVariable Long noteId) {
	    List<Commentaire> commentaires = commentaireRepository.findByNoteId(noteId);
	    if (commentaires.isEmpty()) {
	        // Retourner un 200 OK avec un tableau vide
	        return ResponseEntity.ok(new ArrayList<>());
	    }
	    return ResponseEntity.ok(commentaires);
	}

    @GetMapping
    public List<Commentaire> getAllCommentaires() {
        return commentaireService.getAllCommentaires();
    }
    // Endpoint pour créer un commentaire
    @PostMapping
    public Commentaire addCommentaire(@RequestBody Commentaire commentaire) {
        return commentaireService.createCommentaire(commentaire);
    }

    // Endpoint pour supprimer un commentaire
    @DeleteMapping("/{id}")
    public void deleteCommentaire(@PathVariable Long id) {
        commentaireService.deleteCommentaire(id);
    }
    
    @PostMapping("/partager")
    public ResponseEntity<String> partagerNote(@RequestParam Long noteId, @RequestParam Long utilisateurId) {
        boolean partageReussi = noteService.partagerNoteAvecUtilisateur(noteId, utilisateurId);
        if (partageReussi) {
            return ResponseEntity.ok("Note partagée avec succès");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors du partage de la note");
        }
    }

}
