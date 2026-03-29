package org.foundations.awsproject.repository.teacher;

import org.foundations.awsproject.entities.Teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ISDTeacherRepository extends JpaRepository<Teacher, UUID> {

    @Query("select t from Teacher t where t.isActive = true")
    List<Teacher> findAllActive();

    @Query("select t from Teacher t where t.id = :id and t.isActive = true")
    Optional<Teacher> findActiveById(@Param("id") UUID id);
}
