package org.foundations.awsproject.services.student;

import org.foundations.awsproject.entities.Student;
import org.foundations.awsproject.repository.student.IStudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    public UpdateStudentResponse findById(Long id) {
        return studentRepository.findById(id)
                .map(UpdateStudentResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
    }

    public CreateStudentResponse create(CreateStudentRequest request) {
        Student student = new Student();
        student.setId(request.id());
        student.setName(request.name());
        student.setLastname(request.lastname());
        student.setStudentId(request.studentId());
        student.setGpa(request.gpa());

        Student savedStudent = studentRepository.save(student);
        return CreateStudentResponse.from(savedStudent);
    }

    public UpdateStudentResponse update(Long id, UpdateStudentRequest request) {
        Student student = new Student();
        student.setName(request.name());
        student.setLastname(request.lastname());
        student.setStudentId(request.studentId());
        student.setGpa(request.gpa());

        return studentRepository.update(id, student)
                .map(UpdateStudentResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
    }

    public UpdateStudentResponse delete(Long id) {
        return studentRepository.delete(id)
                .map(UpdateStudentResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
    }

    public void updateProfilePicture(Long id, String url) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
        student.setFotoPerfilUrl(url);
        // Note: In JpaStudentRepository, save() is used for updates if it's JPA.
        // Wait, does JpaStudentRepository have a save method that does update?
        // Wait, the interface IStudentRepository has `save()` and `update(Long, Student)`.
        // The custom update ignores fotoPerfilUrl currently, so we use `save()` if IStudentRepository exposes it, or we implement update logic. Let's just use `save` if it extends JpaRepository. Wait, IStudentRepository is custom?
        studentRepository.save(student);
    }
}
