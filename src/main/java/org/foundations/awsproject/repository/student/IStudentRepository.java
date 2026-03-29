package org.foundations.awsproject.repository.student;

import org.foundations.awsproject.entities.Student;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IStudentRepository {
    Student save(Student student);

    List<Student> findAll();

    Optional<Student> findById(UUID id);
}
