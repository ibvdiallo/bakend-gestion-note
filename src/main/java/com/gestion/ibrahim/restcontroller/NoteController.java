package com.gestion.ibrahim.restcontroller;

import java.io.File;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import com.gestion.ibrahim.entite.Note;
import com.gestion.ibrahim.entite.PartageNote;
import com.gestion.ibrahim.entite.Utilisateur;
import com.gestion.ibrahim.repos.repoNote;
import com.gestion.ibrahim.repos.repoPartage;
import com.gestion.ibrahim.repos.repoUtilisateur;
import com.gestion.ibrahim.services.NoteService;
import com.gestion.ibrahim.services.PartageService;
import com.gestion.ibrahim.services.UtilisateurService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.core.io.ByteArrayResource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


import java.io.ByteArrayOutputStream;

@RestController
@CrossOrigin(origins = "https://notecours.web.app")
@RequestMapping("/api/notes")
//@CrossOrigin(origins = "http://localhost:4200") // Autoriser les requêtes depuis Angular
public class NoteController {

    @Autowired
    private NoteService noteService;
    @Autowired
    private repoNote noteRepository;
    @Autowired
    private PartageService partageService;
    @Autowired
    private UtilisateurService  utilisateurService;
    @Autowired
    private repoUtilisateur utilisateurRepository;
    @Autowired
    private  repoPartage repoPartage;
    
    @GetMapping
    public List<Note> getAllNotes() {
        return noteService.getAllNotes();
    }
    @GetMapping("/{userId}/notesRecues")
    public ResponseEntity<List<Note>> getNotesRecues(@PathVariable("userId") Long userId) {
        List<Note> notesRecues = noteService.getNotesRecues(userId);
        
        // Si aucune note n'est trouvée, renvoyer un statut 204 No Content
        if (notesRecues.isEmpty()) {
            return ResponseEntity.noContent().build(); // Aucun contenu trouvé
        }
        
        // Si des notes sont trouvées, les renvoyer avec un statut 200 OK
        return ResponseEntity.ok(notesRecues);
    }
    @GetMapping("un/{noteId}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long noteId) {
        try {
            Note note = noteService.findById(noteId);
            if (note != null) {
                return ResponseEntity.ok(note);
            } else {
                return ResponseEntity.notFound().build();  // Retourner 404 si la note n'est pas trouvée
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);  // Retourner une réponse 500 en cas d'erreur
        }
    }
    
    @GetMapping("/{userId}/notes")
    public ResponseEntity<List<Note>> getNotesByUserId(@PathVariable String userId) {
        List<Note> notes = noteService.getNotesByUserId(userId);
        if (notes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retourne 204 No Content si aucune note n'est trouvée
        }
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }
    
    @GetMapping("/ajoutes/{userId}")
    public ResponseEntity<List<Note>> getNotesAjoutesByUserId(@PathVariable String userId) {
        List<Note> notes = noteService.getNotesAjoutesByUserId(userId);
        if (notes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retourne 204 No Content si aucune note n'est trouvée
        }
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }


    @PostMapping
    public Note createNote(@RequestBody Note note) {
        return noteService.createNote(note);
    }
    
    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename)  throws IOException  {
    	 Path filePath = Paths.get("src/main/resources/static/notes/" + filename);
         Resource resource = new UrlResource(filePath.toUri());

         if (resource.exists() && resource.isReadable()) {
             return ResponseEntity.ok()
                     .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                     .contentType(MediaType.parseMediaType(Files.probeContentType(filePath))) // Détection MIME dynamique
                     .body(resource);
         } else {
             return ResponseEntity.notFound().build();
         }
     }
 



    @PutMapping("/{id}/telechargement")
    public ResponseEntity<Note> incrementerTelechargements(@PathVariable Long id) {
        try {
            Note note = noteService.incrementerTelechargements(id);
            return ResponseEntity.ok(note);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/ajouter")
    public ResponseEntity<Map<String, String>> ajouterNote(
            @RequestParam("titre") String titre,
            @RequestParam("contenu") String contenu,
            @RequestParam("cours") String cours,
            @RequestParam("auteurId") Long auteurId) {

        Map<String, String> response = new HashMap<>();
        try {
            Note note = noteService.creerNote(titre, contenu, cours, auteurId);
            response.put("message", "Note ajoutée avec succès");
            response.put("noteId", String.valueOf(note.getId()));
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("message", "Erreur lors de l'ajout de la note : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    private String saveFile(MultipartFile fichier) throws IOException {
        // Utilisez un répertoire spécifique pour les fichiers téléchargés
    	String directory = "src/main/resources/static/notes/";  // Relatif au dossier 'resources/static'
        String fileName = System.currentTimeMillis() + "_" + fichier.getOriginalFilename();
        Path filePath = Paths.get(directory, fileName);
        Files.createDirectories(filePath.getParent()); // Crée le dossier si nécessaire
        Files.write(filePath, fichier.getBytes()); // Enregistre le fichier dans ce dossier
        return directory + fileName; // Chemin relatif
    }
    
    @GetMapping("/{noteId}/telecharger")
    public ResponseEntity<Resource> telechargerNote(@PathVariable Long noteId) {
        System.out.println("Requête de téléchargement reçue pour la note ID : " + noteId);

        try {
            // Récupérer la note par ID
            Note note = noteService.findById(noteId);
            if (note == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Créer un document PDF avec les données de la note
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Document document = new Document();

            try {
                PdfWriter.getInstance(document, outputStream);
                document.open();

                // Ajout du titre
                Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
                Paragraph title = new Paragraph("Titre : " + note.getTitre(), titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);

                document.add(new Paragraph("\n")); // Saut de ligne

                // Ajout de la description
                Font descFont = new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC);
                Paragraph description = new Paragraph("Cours : " + note.getCours(), descFont);
                description.setAlignment(Element.ALIGN_LEFT);
                document.add(description);

                document.add(new Paragraph("\n")); // Saut de ligne

                // Ajout du contenu
                Font contentFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
                Paragraph content = new Paragraph(note.getContenu(), contentFont);
                content.setAlignment(Element.ALIGN_JUSTIFIED);
                document.add(content);

            } catch (DocumentException e) {
                System.err.println("Erreur lors de la génération du PDF : " + e.getMessage());
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            } finally {
                document.close();
            }

            // Convertir le PDF en ressource téléchargeable
            ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

            // Retourner la réponse HTTP
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + note.getTitre() + ".pdf\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);

        } catch (Exception e) {
            System.err.println("Erreur lors du téléchargement : " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    
    @GetMapping("/rechercherparnotes")
    public ResponseEntity<List<Note>> rechercherNotes(@RequestParam String query) {
        List<Note> notes = noteService.rechercherParTitreOuContenuOuCours(query);
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/rechercherparutilisateurs")
    public ResponseEntity<List<Utilisateur>> rechercherUtilisateurs(@RequestParam String query) {
        List<Utilisateur> utilisateurs = utilisateurService.rechercherParNomOuEmail(query);
        return ResponseEntity.ok(utilisateurs);
    }
    //@PostMapping("/{noteIds}/partager/{utilisateurIdddddd}")
    //public ResponseEntity<String> partagerNote(
      //      @PathVariable Long noteId,
        //    @PathVariable Long utilisateurId) {
        /*boolean success = noteService.partagerNoteAvecUtilisateur(noteId, utilisateurId);
        if (success) {
            return ResponseEntity.ok("Note partagée avec succès.");
        }
        return ResponseEntity.badRequest().body("Erreur lors du partage de la note.");
    */
    
    //@PostMapping("/{noteId}/partager/{userId}")
    //public void shareNoteWithUser(@PathVariable Long noteId, @PathVariable Long userId) {
     //   noteService.shareNoteWithUser(noteId, userId);
    //}
    
  
    //@GetMapping("/{userId}/partagees")
    //public List<Note> getNotesPartagees(@PathVariable Long userId) {
     //   return noteService.getNotesPartageesWithUser(userId);
    //}
 
    //@GetMapping("/{noteId}/partages")
    //public List<Utilisateur> getPartagesByNote(@PathVariable Long noteId) {
      //  List<PartageNote> partages = repoPartage.findByNoteId(noteId);
        
        // Extraire les utilisateurs ayant partagé la note
       // List<Utilisateur> utilisateurs = partages.stream()
              //  .map(partage -> partage.getUtilisateur())  // Récupérer l'utilisateur de chaque partage
                //.collect(Collectors.toList());
       // return utilisateurs;
    //}

    @GetMapping("/utilisateur/{userId}")
    public ResponseEntity<List<Note>> getNotesByUserId(@PathVariable Long userId) {
    	 // Récupérer toutes les notes de l'utilisateurList<Note> notesRecues = noteService.getNotesRecues(userId);
    	List<Note> notesRecues = noteService.getNotesRecues(userId);
        // Si aucune note n'est trouvée, renvoyer un statut 204 No Content
        if (notesRecues.isEmpty()) {
            return ResponseEntity.noContent().build(); // Aucun contenu trouvé
        }
        
        // Si des notes sont trouvées, les renvoyer avec un statut 200 OK
        return ResponseEntity.ok(notesRecues);
    }
    
    
    
    
    
 

    @PostMapping("/{noteId}/partageermoi/{destinataireId}")
    public ResponseEntity<?> partagerNote(
            @PathVariable Long noteId,
            @PathVariable Long destinataireId,
            @RequestParam Long partageurId) {
        try {
            partageService.partagerNote(noteId, destinataireId, partageurId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{userId}/partagees")
    public ResponseEntity<List<Map<String, Object>>> getNotesPartagees(@PathVariable Long userId) {
        try {
            List<Map<String, Object>> notes = partageService.getNotesPartagees(userId);
            return ResponseEntity.ok(notes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    

    // ✅ 3. Modifier une note
    @PutMapping("/modifier/{noteId}")
    public ResponseEntity<Note> updateNote(@PathVariable Long noteId, @RequestBody Note updatedNote) {
        Optional<Note> note = noteService.updateNote(noteId, updatedNote);
        return note.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
 // ✅ 4. Supprimer une note
    @DeleteMapping("/supprimer/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long noteId) {
        return noteService.deleteNote(noteId) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
