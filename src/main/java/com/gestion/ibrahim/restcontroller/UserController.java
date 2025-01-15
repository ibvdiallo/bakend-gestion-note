package com.gestion.ibrahim.restcontroller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/utilisateur")
public class UserController {

    @PostMapping("/message")
    public ResponseEntity<Map<String, Object>> saluerUtilisateur(@RequestBody Map<String, Object> payload) {
        Map<String, Object> response = new HashMap<>();

        if (payload.containsKey("nom") && payload.containsKey("age")) {
            String nom = (String) payload.get("nom");
            int age;

            try {
                age = Integer.parseInt(payload.get("age").toString());
            } catch (NumberFormatException e) {
                response.put("error", "L'âge doit être un entier valide.");
                return ResponseEntity.badRequest().body(response);
            }

            response.put("message", "Salut " + nom + ", vous avez " + age + " ans.");
            return ResponseEntity.ok(response);

        } else {
            response.put("error", "Nom ou âge manquant.");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
