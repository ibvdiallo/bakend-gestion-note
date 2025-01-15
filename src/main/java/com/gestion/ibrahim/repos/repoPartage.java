package com.gestion.ibrahim.repos;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.ibrahim.entite.PartageNote;

public interface repoPartage extends JpaRepository<PartageNote, Long>  {
	List<PartageNote> findByUtilisateurId(Long utilisateurId);
	
}
