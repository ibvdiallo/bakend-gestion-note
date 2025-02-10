package com.gestion.ibrahim.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.ibrahim.entite.Note;
import com.gestion.ibrahim.entite.PartageNote;
import com.gestion.ibrahim.entite.Utilisateur;
import com.gestion.ibrahim.repos.repoNote;
import com.gestion.ibrahim.repos.repoPartage;
import com.gestion.ibrahim.repos.repoUtilisateur;

@Service
public class NoteImpl implements NoteService{
		@Autowired
	    private repoNote noteRepository;
		
		 @Autowired
		 private repoUtilisateur utilisateurRepository;

		 @Autowired
		 private repoPartage partageNoteRepository;
			
	    @Override
	    public List<Note> getAllNotes() {
	        return noteRepository.findAll();
	    }
	    
	    @Override
	    public  Note findById(Long id) {
	        return noteRepository.findById(id).orElseThrow(()->new RuntimeException("note non trouver"));
	    }

	    @Override
	    public Note createNote(Note note) {
	        return noteRepository.save(note);
	    }

	    @Override
	    public Note incrementerTelechargements(Long id) {
	        Optional<Note> noteOptional = noteRepository.findById(id);
	        if (noteOptional.isPresent()) {
	            Note note = noteOptional.get();
	            note.setNombreTelechargements(note.getNombreTelechargements() + 1);
	            return noteRepository.save(note);
	        } else {
	            throw new RuntimeException("Note introuvable pour l'id : " + id);
	        }
	    }

	    @Override
	    public Note creerNote(String titre, String contenu, String cours, Long auteurId) {
	        Utilisateur auteur = utilisateurRepository.findById(auteurId).orElseThrow(() -> new IllegalArgumentException("Auteur non trouvé"));

	        Note note = new Note();
	        note.setTitre(titre);
	        note.setContenu(contenu);
	        note.setCours(cours);
	        note.setAuteur(auteur);
	        note.setNombreTelechargements(0);

	        return noteRepository.save(note);
	    }
	    
	    public boolean partagerNoteAvecUtilisateur(Long noteId, Long utilisateurId) {
	        // Récupérer l'utilisateur cible
	        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId).orElse(null);
	        if (utilisateur == null) {
	            return false; // Utilisateur introuvable
	        }

	        // Récupérer la note à partager
	        Note note = noteRepository.findById(noteId).orElse(null);
	        if (note == null) {
	            return false; // Note introuvable
	        }

	        PartageNote partage = new PartageNote();
	        partage.setNote(note);
           // partage.setUtilisateur(utilisateur);
	        partageNoteRepository.save(partage); // Enregistrer le partage
	        return true;
	    }
	    @Override
	    public void shareNoteWithUser(Long noteId, Long userId) {
	        Optional<Note> noteOpt = noteRepository.findById(noteId);
	        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findById(userId);

	        if (noteOpt.isPresent() && utilisateurOpt.isPresent()) {
	            Note note = noteOpt.get();
	            Utilisateur utilisateur = utilisateurOpt.get();
	            
	            // Créer une nouvelle entrée de partage
	            PartageNote partageNote = new PartageNote();
	            partageNote.setNote(note);
	          //  partageNote.setUtilisateur(utilisateur);

	            partageNoteRepository.save(partageNote); // Sauvegarder le partage de la note
	        } else {
	            throw new RuntimeException("Note ou utilisateur introuvable");
	        }
	    }
	    /*public List<Note> getNotesPartageesWithUser(Long utilisateurId) {
	        List<PartageNote> partages = partageNoteRepository.findByUtilisateurId(utilisateurId);
	        // Extraire les notes partagées
	        return partages.stream().map(PartageNote::getNote).toList();
	    }*/
		@Override
		public Note incrementerTelechargement(Long noteId) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public List<Note> rechercherParTitreOuContenuOuCours(String query) {
	        return noteRepository.findByTitreContainingIgnoreCaseOrContenuContainingIgnoreCaseOrCoursContainingIgnoreCase(query, query, query);
	    }

		@Override
		public List<Note> getNotesByUserId(String userId) {
			// TODO Auto-generated method stub
			
		       
		        Long userLongId = Long.parseLong(userId); // Convertir l'ID de l'utilisateur de String à Long
		        return noteRepository.findByAuteurId(userLongId);
		      
		    
		}

		

		@Override
		public List<Note> getNotesAjoutesByUserId(String userId) {
			 Long userLongId = Long.parseLong(userId);
			// TODO Auto-generated method stub
			return noteRepository.findByAuteurId(userLongId);
		}

		@Override
		public List<Note> getNotesRecues(Long userId) {
			// TODO Auto-generated method stub
			
		        // Vous pouvez implémenter ici toute logique pour filtrer les notes reçues
			return noteRepository.findByAuteurId(userId); 
		    
		}
		
		
		@Override
	    public Optional<Note> updateNote(Long noteId, Note updatedNote) {
	        Optional<Note> existingNoteOpt = noteRepository.findById(noteId);
	        if (existingNoteOpt.isPresent()) {
	            Note existingNote = existingNoteOpt.get();
	            existingNote.setTitre(updatedNote.getTitre());
	            existingNote.setCours(updatedNote.getCours());
	            existingNote.setContenu(updatedNote.getContenu());
	            noteRepository.save(existingNote);
	            return Optional.of(existingNote);
	        }
	        return Optional.empty();
	    }

	    @Override
	    public boolean deleteNote(Long noteId) {
	        if (noteRepository.existsById(noteId)) {
	            noteRepository.deleteById(noteId);
	            return true;
	        }
	        return false;
	    }
		

}
