package org.foundations.awsproject.repository.teacher;

import org.foundations.awsproject.entities.Teacher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface
ITeacherRepository {
    Teacher save(Teacher student);

    List<Teacher> findAll();

    Optional<Teacher> findById(UUID id);
}
