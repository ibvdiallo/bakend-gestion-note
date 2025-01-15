package com.gestion.ibrahim.services;

import java.util.List;

import com.gestion.ibrahim.entite.Commentaire;
import com.gestion.ibrahim.entite.Utilisateur;
import java.util.Optional;

public interface UtilisateurService {
	
	List<Utilisateur> getAllUtilisateurs();
	
    Utilisateur createUtilisateur(Utilisateur utilisateur);
    
    Utilisateur inscriptionUtilisateur(Utilisateur utilisateur);
    
    boolean existsByEmail(String email);
    
   
    
   // Utilisateur findByEmail(String email);
    
    Utilisateur updateUtilisateur(Utilisateur u);
	
	void deleteUtilisateur(Utilisateur u);
	
	void deleteUtilisateurById(Long id);
	
	
	Utilisateur findByEmail(String email);
	List<Commentaire> getCommentairesByUtilisateurId(Long utilisateurId);

	Utilisateur findById(Long id);
	List<Utilisateur> rechercherParNomOuEmail(String query);


}
