package org.foundations.awsproject.services.student;

import org.foundations.awsproject.entities.Student;
import org.foundations.awsproject.repository.student.IStudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class StudentService {

    private final IStudentRepository studentRepository;

    public StudentService(IStudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<UpdateStudentResponse> findAll() {
        return studentRepository.findAll().stream()
                .map(UpdateStudentResponse::from)
                .toList();
    }

    public UpdateStudentResponse findById(UUID id) {
        return studentRepository.findById(id)
                .map(UpdateStudentResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
    }

    public CreateStudentResponse create(CreateStudentRequest request) {
        Student student = new Student();
        student.setName(request.name());
        student.setLastname(request.lastname());
        student.setStudentId(request.studentId());
        student.setGpa(request.gpa());

        Student savedStudent = studentRepository.save(student);
        return CreateStudentResponse.from(savedStudent);
    }

    public UpdateStudentResponse update(UUID id, UpdateStudentRequest request) {
        Student student = new Student();
        student.setName(request.name());
        student.setLastname(request.lastname());
        student.setStudentId(request.studentId());
        student.setGpa(request.gpa());

        return studentRepository.update(id, student)
                .map(UpdateStudentResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
    }

    public UpdateStudentResponse delete(UUID id) {
        return studentRepository.delete(id)
                .map(UpdateStudentResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
    }
}
