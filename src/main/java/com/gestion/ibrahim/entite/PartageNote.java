package com.gestion.ibrahim.entite;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
@Entity
@Data
@Getter
@Setter
public class PartageNote {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name = "note_id", nullable = false)
	    private Note note;

	    @ManyToOne
	    @JoinColumn(name = "utilisateur_destinataire_id")
	    private Utilisateur destinataire;

	    @ManyToOne
	    @JoinColumn(name = "utilisateur_partageur_id")
	    private Utilisateur partageur;
	    // Vous pouvez ajouter une date de partage si nécessaire
	    @Column(nullable = false, updatable = false)
	    private LocalDateTime datePartage = LocalDateTime.now();
	
}

