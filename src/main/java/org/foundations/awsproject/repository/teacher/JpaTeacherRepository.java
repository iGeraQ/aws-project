package org.foundations.awsproject.repository.teacher;

import org.foundations.awsproject.entities.Teacher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("jpa")
class JpaTeacherRepository implements ITeacherRepository {

    private final ISDTeacherRepository springTeacherRepository;

    JpaTeacherRepository(ISDTeacherRepository springTeacherRepository){
        this.springTeacherRepository = springTeacherRepository;
    }

    @Override
    public Teacher save(Teacher teacher) {
        return this.springTeacherRepository.save(teacher);
    }

    @Override
    public Optional<Teacher> update(UUID id, Teacher teacher) {
        return this.springTeacherRepository.findActiveById(id)
                .map(existingTeacher -> {
                    if (teacher.getName() != null) {
                        existingTeacher.setName(teacher.getName());
                    }
                    if (teacher.getLastname() != null) {
                        existingTeacher.setLastname(teacher.getLastname());
                    }
                    existingTeacher.setClassHour(teacher.getClassHour());
                    existingTeacher.setEmployeeId(teacher.getEmployeeId());
                    return this.springTeacherRepository.save(existingTeacher);
                });
    }

    @Override
    public Optional<Teacher> delete(UUID id) {
        return this.springTeacherRepository.findActiveById(id)
                .map(existingTeacher -> {
                    existingTeacher.deactivate();
                    return this.springTeacherRepository.save(existingTeacher);
                });
    }

    @Override
    public List<Teacher> findAll() {
        return this.springTeacherRepository.findAllActive();
    }

    @Override
    public Optional<Teacher> findById(UUID id) {
        return this.springTeacherRepository.findActiveById(id);
    }
}
