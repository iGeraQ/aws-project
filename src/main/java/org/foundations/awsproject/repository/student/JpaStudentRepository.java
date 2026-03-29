package org.foundations.awsproject.repository.student;

import org.foundations.awsproject.entities.Student;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("jpa")
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
    public Optional<Student> update(UUID id, Student student) {
        return this.springStudentRepository.findActiveById(id)
                .map(existingStudent -> {
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
                    return this.springStudentRepository.save(existingStudent);
                });
    }

    @Override
    public Optional<Student> delete(UUID id) {
        return this.springStudentRepository.findActiveById(id)
                .map(existingStudent -> {
                    existingStudent.deactivate();
                    return this.springStudentRepository.save(existingStudent);
                });
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
