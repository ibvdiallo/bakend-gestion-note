package com.gestion.ibrahim.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.ibrahim.entite.Note;
import com.gestion.ibrahim.entite.PartageNote;
import com.gestion.ibrahim.repos.repoNote;
import com.gestion.ibrahim.repos.repoPartage;
import com.gestion.ibrahim.repos.repoUtilisateur;

@Service
public class PartageImpl  implements PartageService{

	@Autowired
    private repoPartage partageNoteRepository;
	
	@Autowired
    private repoNote noteRepository;

	@Override
	public boolean partagerNoteAvecUtilisateur(Long noteId, Long utilisateurId) {
		PartageNote partage = new PartageNote();
        partage.setUtilisateurId(utilisateurId);
        partage.setNoteId(noteId);
        partageNoteRepository.save(partage);
        return true;
	}

	@Override
	public List<Note> recupererNotesPartagees(Long utilisateurId) {
		 List<PartageNote> partages = partageNoteRepository.findByUtilisateurId(utilisateurId);
	        List<Long> noteIds = partages.stream().map(PartageNote::getNoteId).toList();
	        return noteRepository.findAllById(noteIds);
	}

}
