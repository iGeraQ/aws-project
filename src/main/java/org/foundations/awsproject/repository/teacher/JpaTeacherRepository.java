package org.foundations.awsproject.repository.teacher;

import org.foundations.awsproject.entities.Teacher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
class JpaTeacherRepository implements ITeacherRepository {

    private final ISDTeacherRepository springTeacherRepository;

    JpaTeacherRepository(ISDTeacherRepository springTeacherRepository){
        this.springTeacherRepository = springTeacherRepository;
    }

    @Override
    public Teacher save(Teacher student) {
        return this.springTeacherRepository.save(student);
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
