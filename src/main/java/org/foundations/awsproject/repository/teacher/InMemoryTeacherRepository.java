package org.foundations.awsproject.repository.teacher;

import org.foundations.awsproject.entities.Teacher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("memory")
class InMemoryTeacherRepository implements ITeacherRepository {

    private final Map<Long, Teacher> teachers = new ConcurrentHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(1);

    @Override
    public Teacher save(Teacher teacher) {
        LocalDate now = LocalDate.now();

        if (teacher.getId() == null) {
            teacher.setId(idSequence.getAndIncrement());
        } else {
            idSequence.updateAndGet(current -> Math.max(current, teacher.getId() + 1));
        }

        if (teacher.getCreatedAt() == null) {
            teacher.setCreatedAt(now);
        }

        teacher.setUpdatedAt(now);
        teachers.put(teacher.getId(), teacher);
        return teacher;
    }

    @Override
    public Optional<Teacher> update(Long id, Teacher teacher) {
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
    public Optional<Teacher> delete(Long id) {
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
    public Optional<Teacher> findById(Long id) {
        return Optional.ofNullable(teachers.get(id))
                .filter(Teacher::isActive);
    }
}
