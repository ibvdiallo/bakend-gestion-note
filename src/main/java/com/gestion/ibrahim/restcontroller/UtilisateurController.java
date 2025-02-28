package com.gestion.ibrahim.restcontroller;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestion.ibrahim.entite.Note;
import com.gestion.ibrahim.entite.Utilisateur;
import com.gestion.ibrahim.repos.repoNote;
import com.gestion.ibrahim.repos.repoUtilisateur;
import com.gestion.ibrahim.services.UtilisateurService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("/api/utilisateurs")
@CrossOrigin(origins = "https://notecours.web.app")
public class UtilisateurController {
	@Autowired
    private UtilisateurService utilisateurService;
	
	@Autowired
    private repoUtilisateur utilisateurRepository;
	
	@Autowired
    private repoNote noteRepository;
	
    @GetMapping
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurService.getAllUtilisateurs();
    }

    @PostMapping
    public Utilisateur createUtilisateur(@RequestBody Utilisateur utilisateur) {
        return utilisateurService.createUtilisateur(utilisateur);
    }
    @PutMapping("/modifier/{userId}")
    public ResponseEntity<Utilisateur> updateUtilisateurs(Long userId, Utilisateur updateUtilisateur) {
        Optional<Utilisateur> utilisateur = utilisateurService.updateUtilisateurs(userId, updateUtilisateur);
        return utilisateur.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUtilisateurById(@PathVariable("id") Long id) {
        try {
            Utilisateur utilisateur = utilisateurService.findById(id);

            if (utilisateur != null) {
                // Récupérer le nombre de notes
                Long nombreNotes = noteRepository.countByAuteur_Id(id);
                
                
                Map<String, Object> response = new HashMap<>();
                response.put("id", utilisateur.getId());
                response.put("nom", utilisateur.getNom());
                response.put("prenom", utilisateur.getPrenom());
                response.put("email", utilisateur.getEmail());
                response.put("motDePasse", utilisateur.getMotDePasse());
                response.put("nombreNotes", nombreNotes);
                response.put("imagePath", utilisateur.getImagePath()); // URL de l'image
                

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Utilisateur non trouvé"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Erreur serveur"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Utilisateur utilisateur) {
        Utilisateur existingUtilisateur = utilisateurService.findByEmail(utilisateur.getEmail());
        
        if (existingUtilisateur != null && existingUtilisateur.getMotDePasse().equals(utilisateur.getMotDePasse())) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Connexion réussie !");
            response.put("id", existingUtilisateur.getId());
            response.put("nom", existingUtilisateur.getNom());
            response.put("prenom", existingUtilisateur.getPrenom());
            response.put("email", existingUtilisateur.getEmail());
            response.put("imagePath", existingUtilisateur.getImagePath()); // Ajoute l'URL de l'image
            
            return ResponseEntity.ok(response);
        }
        
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "Identifiants incorrects !");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    
    @PostMapping("/inscription")
    public ResponseEntity<Map<String, String>> inscription(
            @RequestPart("utilisateur") String utilisateurJson, 
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        
        Map<String, String> response = new HashMap<>();
        if (image != null) {
            System.out.println("Nom du fichier reçu : " + image.getOriginalFilename());
           

        } else {
            System.out.println("Aucune image reçue");
        }

        
        try {
            // Désérialisation de l'utilisateur à partir du JSON
            ObjectMapper objectMapper = new ObjectMapper();
            Utilisateur utilisateur = objectMapper.readValue(utilisateurJson, Utilisateur.class);

            // Gestion de l'image
            if (image != null && !image.isEmpty()) {
                String imagePath = saveImage(image);
                utilisateur.setImagePath(imagePath);

                // Affiche le chemin absolu pour vérification
                System.out.println("Chemin absolu de l'image enregistrée : " + imagePath);
                
                
            }

            // Appel à la méthode d'inscription du service
            Utilisateur newUtilisateur = utilisateurService.inscriptionUtilisateur(utilisateur);
            response.put("message", "Inscription réussie !");
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    private String saveImage(MultipartFile image) throws IOException {
    	 String directory = "/Applications/XAMPP/xamppfiles/htdocs/tesphp/images/"; // Chemin vers le dossier de stockage des images
    	    String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
    	    Path imagePath = Paths.get(directory, fileName);
    	    Files.createDirectories(imagePath.getParent()); // Crée le dossier si nécessaire
    	    Files.write(imagePath, image.getBytes()); // Enregistre l'image dans ce dossier

    	    // Affiche le chemin absolu pour vérification
    	    System.out.println("Chemin absolu de l'image enregistrée : " + imagePath.toAbsolutePath());
    	    
    	    return "images/" + fileName;
    }
    
    @GetMapping("/{userId}/profil")
    public ResponseEntity<?> getProfilUtilisateur(@PathVariable Long userId) {
        return utilisateurRepository.findById(userId)
            .map(user -> {
                long nombreNotes = noteRepository.countByAuteurId(userId);
                return ResponseEntity.ok(new ProfilResponse(
                    user.getNom(),
                    user.getPrenom(),
                    user.getEmail(),
                    user.getImagePath(), // 📸 Image de profil ajoutée
                    nombreNotes
                ));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Classe pour structurer la réponse avec l'image
    record ProfilResponse(String nom, String prenom, String email, String imageProfil, long nombreNotes) {}
}

