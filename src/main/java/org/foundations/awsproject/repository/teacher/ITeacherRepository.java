package org.foundations.awsproject.repository.teacher;

import org.foundations.awsproject.entities.Teacher;

import java.util.List;
import java.util.Optional;

public interface
ITeacherRepository {
    Teacher save(Teacher teacher);

    Optional<Teacher> update(Long id, Teacher teacher);

    Optional<Teacher> delete(Long id);

    List<Teacher> findAll();

    Optional<Teacher> findById(Long id);
}
