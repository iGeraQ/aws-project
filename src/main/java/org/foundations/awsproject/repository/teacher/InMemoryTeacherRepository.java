package org.foundations.awsproject.repository.teacher;

import org.foundations.awsproject.entities.Teacher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("memory")
class InMemoryTeacherRepository implements ITeacherRepository {

    private final Map<UUID, Teacher> teachers = new ConcurrentHashMap<>();

    @Override
    public Teacher save(Teacher teacher) {
        LocalDate now = LocalDate.now();

        if (teacher.getId() == null) {
            teacher.setId(UUID.randomUUID());
            if (teacher.getCreatedAt() == null) {
                teacher.setCreatedAt(now);
            }
        }

        teacher.setUpdatedAt(now);
        teachers.put(teacher.getId(), teacher);
        return teacher;
    }

    @Override
    public Optional<Teacher> update(UUID id, Teacher teacher) {
        Teacher existingTeacher = teachers.get(id);
        if (existingTeacher == null || !existingTeacher.isActive()) {
            return Optional.empty();
        }

        if (teacher.getName() != null) {
            existingTeacher.setName(teacher.getName());
        }
        if (teacher.getLastname() != null) {
            existingTeacher.setLastname(teacher.getLastname());
        }
        existingTeacher.setClassHour(teacher.getClassHour());
        existingTeacher.setEmployeeId(teacher.getEmployeeId());
        existingTeacher.setUpdatedAt(LocalDate.now());
        teachers.put(id, existingTeacher);
        return Optional.of(existingTeacher);
    }

    @Override
    public Optional<Teacher> delete(UUID id) {
        Teacher existingTeacher = teachers.get(id);
        if (existingTeacher == null || !existingTeacher.isActive()) {
            return Optional.empty();
        }

        existingTeacher.deactivate();
        existingTeacher.setUpdatedAt(LocalDate.now());
        teachers.put(id, existingTeacher);
        return Optional.of(existingTeacher);
    }

    @Override
    public List<Teacher> findAll() {
        return teachers.values().stream()
                .filter(Teacher::isActive)
                .toList();
    }

    @Override
    public Optional<Teacher> findById(UUID id) {
        return Optional.ofNullable(teachers.get(id))
                .filter(Teacher::isActive);
    }
}
