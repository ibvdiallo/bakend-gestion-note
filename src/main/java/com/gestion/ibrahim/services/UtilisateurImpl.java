package com.gestion.ibrahim.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.ibrahim.entite.Commentaire;
import com.gestion.ibrahim.entite.Utilisateur;
import com.gestion.ibrahim.repos.repoCommentaire;
import com.gestion.ibrahim.repos.repoUtilisateur;

@Service
public class UtilisateurImpl  implements UtilisateurService{
	
	@Autowired
    private repoUtilisateur utilisateurRepository;

	

    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    @Override
    public Utilisateur createUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }
    
    @Override
    public Utilisateur inscriptionUtilisateur(Utilisateur utilisateur) {
        // Vérifie si l'email existe déjà
        if (utilisateurRepository.existsByEmail(utilisateur.getEmail())) {
            throw new IllegalArgumentException("Cet email est déjà utilisé !");
        }
        return utilisateurRepository.save(utilisateur);
    }
	/*@Override
	public Utilisateur updateUtilisateur(Utilisateur u) {
		// TODO Auto-generated method stub
		return utilisateurRepository.save(u);
	}*/
	@Override
	public void deleteUtilisateur(Utilisateur u) {
		// TODO Auto-generated method stub
		utilisateurRepository.delete(u);
	}
	@Override
	public void deleteUtilisateurById(Long id) {
		// TODO Auto-generated method stub
		utilisateurRepository.deleteById(id);
	}
	
	@Override
    public Utilisateur findByEmail(String email) {
        return utilisateurRepository.findByEmail(email); // Méthode dans le repository
    }

	@Override
	public boolean existsByEmail(String email) {
		// TODO Auto-generated method stub
		return false;
	}



	 @Autowired
	    private repoCommentaire commentaireRepository;  // Injection du repoCommentaire

	    @Override
	    public List<Commentaire> getCommentairesByUtilisateurId(Long utilisateurId) {
	        return commentaireRepository.findByUtilisateurId(utilisateurId);  // Utilisation du repoCommentaire
	    }

	    @Override
	    public Utilisateur findById(Long id) {
	        // Vérifier si l'utilisateur existe dans la base de données
	        return utilisateurRepository.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé avec l'ID: " + id));
	    }
	    @Override
	    public List<Utilisateur> rechercherParNomOuEmail(String query) {
	        return utilisateurRepository.findByNomContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query);
	    }
		
	    @Override
	    public Optional<Utilisateur> updateUtilisateurs(Long userId, Utilisateur updateUtilisateur) {
	        Optional<Utilisateur> existingUtilsateurOpt = utilisateurRepository.findById(userId);
	        if (existingUtilsateurOpt.isPresent()) {
	            Utilisateur existingUtilisateur = existingUtilsateurOpt.get();
	            existingUtilisateur.setMotDePasse(updateUtilisateur.getMotDePasse());
	           
	            utilisateurRepository.save(existingUtilisateur);
	            return Optional.of(existingUtilisateur);
	        }
	        return Optional.empty();
	    }

}
