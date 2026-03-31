package org.foundations.awsproject.controllers;

import jakarta.validation.Valid;
import org.foundations.awsproject.services.teacher.CreateTeacherRequest;
import org.foundations.awsproject.services.teacher.CreateTeacherResponse;
import org.foundations.awsproject.services.teacher.TeacherService;
import org.foundations.awsproject.services.teacher.UpdateTeacherRequest;
import org.foundations.awsproject.services.teacher.UpdateTeacherResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/profesores")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public ResponseEntity<List<UpdateTeacherResponse>> findAll() {
        return ResponseEntity.ok(this.teacherService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UpdateTeacherResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(this.teacherService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CreateTeacherResponse> create(@Valid @RequestBody CreateTeacherRequest body) {
        CreateTeacherResponse teacher = this.teacherService.create(body);
        return ResponseEntity.created(URI.create("/api/profesores/" + teacher.id())).body(teacher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateTeacherResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateTeacherRequest body) {
        UpdateTeacherResponse teacher = this.teacherService.update(id, body);
        return ResponseEntity.ok(teacher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.teacherService.delete(id);
        return ResponseEntity.ok().build();
    }
}
