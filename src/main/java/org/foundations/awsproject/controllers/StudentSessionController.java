package org.foundations.awsproject.controllers;

import org.foundations.awsproject.entities.StudentSession;
import org.foundations.awsproject.services.StudentSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/alumnos/{id}/session")
public class StudentSessionController {

    private final StudentSessionService sessionService;

    public StudentSessionController(StudentSessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String password = request.get("password");
        if (password == null) {
            return ResponseEntity.badRequest().body("Falta el campo password");
        }
        try {
            StudentSession session = sessionService.login(id, password);
            return ResponseEntity.ok(session);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String sessionString = request.get("sessionString");
        if (sessionString == null) {
            return ResponseEntity.badRequest().body("Falta el campo sessionString");
        }
        boolean isValid = sessionService.verify(sessionString);
        if (isValid) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("Sesión inválida o inactiva");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String sessionString = request.get("sessionString");
        if (sessionString == null) {
            return ResponseEntity.badRequest().body("Falta el campo sessionString");
        }
        sessionService.logout(sessionString);
        return ResponseEntity.ok().build();
    }
}
