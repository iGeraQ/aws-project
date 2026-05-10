package org.foundations.awsproject.controllers;


import jakarta.validation.Valid;
import org.foundations.awsproject.services.S3Service;
import org.foundations.awsproject.services.student.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/alumnos")
public class StudentController {

    private final StudentService studentService;
    private final S3Service s3Service;
    private final org.foundations.awsproject.services.SnsService snsService;

    // Es buena práctica que el constructor sea público
    public StudentController(StudentService studentService, S3Service s3Service, org.foundations.awsproject.services.SnsService snsService){
        this.studentService = studentService;
        this.s3Service = s3Service;
        this.snsService = snsService;
    }

    @GetMapping
    public ResponseEntity<List<UpdateStudentResponse>> findAll(){
        return ResponseEntity.ok(this.studentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UpdateStudentResponse> findById(@PathVariable Long id){
        // Si el service lanza una excepción si no existe, Spring la capturará
        return ResponseEntity.ok(this.studentService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CreateStudentResponse> create(@Valid @RequestBody CreateStudentRequest body){
        CreateStudentResponse student = this.studentService.create(body);
        return ResponseEntity.created(URI.create("/api/alumnos/" + student.id())).body(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateStudentResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateStudentRequest body){
        UpdateStudentResponse student = this.studentService.update(id, body);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.studentService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/{id}/fotoPerfil", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadProfilePicture(@PathVariable Long id, @RequestParam("foto") MultipartFile file) {
        try {
            this.studentService.findById(id);

            String url = s3Service.uploadProfilePicture(id, file);

            this.studentService.updateProfilePicture(id, url);

            return ResponseEntity.ok().body(Map.of("fotoPerfilUrl", url));
        } catch (org.springframework.web.server.ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/email")
    public ResponseEntity<?> sendEmailNotification(@PathVariable Long id, @RequestBody @Valid UpdateStudentRequest request) {
        try {
            UpdateStudentResponse student = this.studentService.update(id, request);
            this.snsService.sendStudentEmail(student);
            return ResponseEntity.ok(student);
        } catch (org.springframework.web.server.ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
