package org.foundations.awsproject.repository.student;

import org.foundations.awsproject.entities.Student;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IStudentRepository {
    Student save(Student student);

    Optional<Student> update(UUID id, Student student);

    Optional<Student> delete(UUID id);

    List<Student> findAll();

    Optional<Student> findById(UUID id);
}
