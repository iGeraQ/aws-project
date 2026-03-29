package org.foundations.awsproject.controllers;


import jakarta.validation.Valid;
import org.foundations.awsproject.services.student.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/alumnos")
public class StudentController {

    private final StudentService studentService;

    // Es buena práctica que el constructor sea público
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<UpdateStudentResponse>> findAll(){
        return ResponseEntity.ok(this.studentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UpdateStudentResponse> findById(@PathVariable UUID id){
        // Si el service lanza una excepción si no existe, Spring la capturará
        return ResponseEntity.ok(this.studentService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CreateStudentResponse> create(@Valid @RequestBody CreateStudentRequest body){
        CreateStudentResponse student = this.studentService.create(body);
        return ResponseEntity.created(URI.create("/api/alumnos/" + student.id())).body(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateStudentResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdateStudentRequest body){
        UpdateStudentResponse student = this.studentService.update(id, body);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        this.studentService.delete(id); // El retorno del service se ignora porque enviamos 204
        return ResponseEntity.noContent().build();
    }
}
