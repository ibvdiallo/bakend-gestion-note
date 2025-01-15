package com.gestion.ibrahim.restcontroller;

import java.util.HashMap;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gestion.ibrahim.entite.Utilisateur;
import com.gestion.ibrahim.services.UtilisateurService;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UtilisateurService utilisateurService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Utilisateur utilisateur) {
        Utilisateur existingUtilisateur = utilisateurService.findByEmail(utilisateur.getEmail());
        
        if (existingUtilisateur != null && existingUtilisateur.getMotDePasse().equals(utilisateur.getMotDePasse())) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Connexion réussie !");
            response.put("nom", existingUtilisateur.getNom());
            response.put("email", existingUtilisateur.getEmail());
            response.put("id", existingUtilisateur.getId()); // Ajout de l'ID utilisateur pour une éventuelle utilisation
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants incorrects !");
    }
}
