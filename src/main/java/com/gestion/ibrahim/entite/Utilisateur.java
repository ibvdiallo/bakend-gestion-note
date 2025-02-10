package com.gestion.ibrahim.entite;

import java.util.HashSet;

import java.util.Set;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
public class Utilisateur {
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false, unique = true)
	    private String email;

	    @Column(nullable = false)
	    private String motDePasse;

	    @Column(nullable = false)
	    private String nom;

	    @Column(nullable = false)
	    private String prenom;
	    
	    // Chemin de l'image dans le projet Angular
	    @Column
	    private String imagePath;
	    
	 

}
