package org.foundations.awsproject.repository.student;

import org.foundations.awsproject.entities.Student;

import java.util.List;
import java.util.Optional;

public interface IStudentRepository {
    Student save(Student student);

    Optional<Student> update(Long id, Student student);

    Optional<Student> delete(Long id);

    List<Student> findAll();

    Optional<Student> findById(Long id);
}
