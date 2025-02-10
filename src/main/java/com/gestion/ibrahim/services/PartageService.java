package com.gestion.ibrahim.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.ibrahim.entite.Note;
import com.gestion.ibrahim.entite.Utilisateur;
import com.gestion.ibrahim.repos.repoNote;
import com.gestion.ibrahim.repos.repoPartage;
import com.gestion.ibrahim.repos.repoUtilisateur;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.gestion.ibrahim.entite.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.ibrahim.entite.Note;
import com.gestion.ibrahim.entite.PartageNote;
import com.gestion.ibrahim.repos.repoNote;
import com.gestion.ibrahim.repos.repoPartage;
import com.gestion.ibrahim.repos.repoUtilisateur;
import java.util.List;
import java.util.Map;
@Service
public class PartageService {
	
	    @Autowired
	    private repoPartage partageRepository;

	    @Autowired
	    private repoNote noteRepository;

	    @Autowired
	    private repoUtilisateur utilisateurRepository;

	    public void partagerNote(Long noteId, Long destinataireId, Long partageurId) {
	        Note note = noteRepository.findById(noteId)
	            .orElseThrow(() -> new RuntimeException("Note non trouvée"));
	        
	        Utilisateur destinataire = utilisateurRepository.findById(destinataireId)
	            .orElseThrow(() -> new RuntimeException("Destinataire non trouvé"));
	        
	        Utilisateur partageur = utilisateurRepository.findById(partageurId)
	            .orElseThrow(() -> new RuntimeException("Partageur non trouvé"));

	        PartageNote partage = new PartageNote();
	        partage.setNote(note);
	        partage.setDestinataire(destinataire);
	        partage.setPartageur(partageur);
	        partage.setDatePartage(LocalDateTime.now());

	        partageRepository.save(partage);
	    }

	    public List<Map<String, Object>> getNotesPartagees(Long userId) {
	        List<PartageNote> partages = partageRepository.findByDestinataire_Id(userId);
	        
	        return partages.stream().map(partage -> {
	            Map<String, Object> noteInfo = new HashMap<>();
	            Note note = partage.getNote();
	            Utilisateur partageur = partage.getPartageur();

	            noteInfo.put("id", note.getId());
	            noteInfo.put("titre", note.getTitre());
	            noteInfo.put("contenu", note.getContenu());
	            noteInfo.put("cours", note.getCours());
	            noteInfo.put("dateAjout", note.getDateAjout());
	            
	            Map<String, String> partagePar = new HashMap<>();
	            partagePar.put("id", partageur.getId().toString());
	            partagePar.put("nom", partageur.getNom());
	            partagePar.put("prenom", partageur.getPrenom());
	            
	            noteInfo.put("partagePar", partagePar);
	            noteInfo.put("datePartage", partage.getDatePartage());

	            return noteInfo;
	        }).collect(Collectors.toList());
	    }
	}
