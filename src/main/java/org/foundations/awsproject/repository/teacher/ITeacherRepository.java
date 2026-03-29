package org.foundations.awsproject.repository.teacher;

import org.foundations.awsproject.entities.Teacher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface
ITeacherRepository {
    Teacher save(Teacher teacher);

    Optional<Teacher> update(UUID id, Teacher teacher);

    Optional<Teacher> delete(UUID id);

    List<Teacher> findAll();

    Optional<Teacher> findById(UUID id);
}
