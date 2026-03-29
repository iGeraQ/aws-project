package org.foundations.awsproject.services.student;

import org.foundations.awsproject.entities.Student;

import java.time.LocalDate;
import java.util.UUID;

public record UpdateStudentResponse(
        UUID id,
        String name,
        String lastname,
        String studentId,
        float gpa,
        boolean isActive,
        LocalDate createdAt,
        LocalDate updatedAt
) {

    static UpdateStudentResponse from(Student student){
        return new UpdateStudentResponse(
                student.getId(),
                student.getName(),
                student.getLastname(),
                student.getStudentId(),
                student.getGpa(),
                student.isActive(),
                student.getCreatedAt(),
                student.getUpdatedAt()

        );
    }
}
