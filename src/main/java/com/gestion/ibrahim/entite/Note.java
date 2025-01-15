package com.gestion.ibrahim.entite;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Note {

	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false)
	    private String titre;

	    @Column(nullable = false)
	    private String contenu;

	    @Column(nullable = false)
	    private String cours;
	    
	   

	    @ManyToOne
	    @JoinColumn(name = "utilisateur_id", nullable = false)
	    private Utilisateur auteur;

	    @Column(nullable = false)
	    private int nombreTelechargements = 0; 
	    
	    @CreationTimestamp
	    @Column(nullable = false, updatable = false)
	    private LocalDateTime dateAjout; 
	 // Getter pour formater la date en ISO 8601
	    public String getDateAjoutAsString() {
	        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
	        return dateAjout.format(formatter);
	    }
}
