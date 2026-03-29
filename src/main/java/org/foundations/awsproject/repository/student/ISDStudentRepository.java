package org.foundations.awsproject.repository.student;

import org.foundations.awsproject.entities.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ISDStudentRepository extends JpaRepository<Student, UUID> {

    @Query("select s from Student s where s.isActive = true")
    List<Student> findAllActive();

    @Query("select s from Student s where s.id = :id and s.isActive = true")
    Optional<Student> findActiveById(@Param("id") UUID id);
}
