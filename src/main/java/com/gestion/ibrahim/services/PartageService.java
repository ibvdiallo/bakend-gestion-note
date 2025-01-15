package com.gestion.ibrahim.services;

import java.util.List;

import com.gestion.ibrahim.entite.Note;
public interface PartageService {
	
	boolean partagerNoteAvecUtilisateur(Long noteId, Long utilisateurId) ;
	List<Note> recupererNotesPartagees(Long utilisateurId);
}
