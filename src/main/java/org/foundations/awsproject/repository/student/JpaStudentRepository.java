package org.foundations.awsproject.repository.student;

import org.foundations.awsproject.entities.Student;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
class JpaStudentRepository implements IStudentRepository{

    private final ISDStudentRepository springStudentRepository;

    JpaStudentRepository(ISDStudentRepository springStudentRepository){
        this.springStudentRepository = springStudentRepository;
    }

    @Override
    public Student save(Student student) {
        return this.springStudentRepository.save(student);
    }

    @Override
    public List<Student> findAll() {
        return this.springStudentRepository.findAllActive();
    }

    @Override
    public Optional<Student> findById(UUID id) {
        return this.springStudentRepository.findActiveById(id);
    }
}
