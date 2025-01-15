package com.gestion.ibrahim.services;

import java.util.List;


import com.gestion.ibrahim.entite.Note;
import com.gestion.ibrahim.entite.Utilisateur;

public interface NoteService {
	List<Note> getAllNotes();
    Note createNote(Note note);
    Note creerNote(String titre, String contenu, String cours, Long auteurId); // Supprimé fichierPath
    Note incrementerTelechargements(Long id);
    boolean partagerNoteAvecUtilisateur(Long noteId, Long utilisateurId);
    Note incrementerTelechargement(Long noteId);
    Note findById(Long id);
   void shareNoteWithUser(Long noteId, Long userId);
       
    
    List<Note> rechercherParTitreOuContenuOuCours(String query);
    
    
    List<Note> getNotesByUserId(String userId);
    
    List<Note> getNotesAjoutesByUserId(String userId);
    
    List<Note> getNotesRecues(Long userId);
    
    
	List<Note> getNotesPartageesWithUser(Long userId);
    
    
}
