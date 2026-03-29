package org.foundations.awsproject.repository.student;

import org.foundations.awsproject.entities.Student;
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
class InMemoryStudentRepository implements IStudentRepository {

    private final Map<UUID, Student> students = new ConcurrentHashMap<>();

    @Override
    public Student save(Student student) {
        LocalDate now = LocalDate.now();

        if (student.getId() == null) {
            student.setId(UUID.randomUUID());
            if (student.getCreatedAt() == null) {
                student.setCreatedAt(now);
            }
        }

        student.setUpdatedAt(now);
        students.put(student.getId(), student);
        return student;
    }

    @Override
    public Optional<Student> update(UUID id, Student student) {
        Student existingStudent = students.get(id);
        if (existingStudent == null || !existingStudent.isActive()) {
            return Optional.empty();
        }

        if (student.getName() != null) {
            existingStudent.setName(student.getName());
        }
        if (student.getLastname() != null) {
            existingStudent.setLastname(student.getLastname());
        }
        if (student.getStudentId() != null) {
            existingStudent.setStudentId(student.getStudentId());
        }
        existingStudent.setGpa(student.getGpa());
        existingStudent.setUpdatedAt(LocalDate.now());
        students.put(id, existingStudent);
        return Optional.of(existingStudent);
    }

    @Override
    public Optional<Student> delete(UUID id) {
        Student existingStudent = students.get(id);
        if (existingStudent == null || !existingStudent.isActive()) {
            return Optional.empty();
        }

        existingStudent.deactivate();
        existingStudent.setUpdatedAt(LocalDate.now());
        students.put(id, existingStudent);
        return Optional.of(existingStudent);
    }

    @Override
    public List<Student> findAll() {
        return students.values().stream()
                .filter(Student::isActive)
                .toList();
    }

    @Override
    public Optional<Student> findById(UUID id) {
        return Optional.ofNullable(students.get(id))
                .filter(Student::isActive);
    }
}
