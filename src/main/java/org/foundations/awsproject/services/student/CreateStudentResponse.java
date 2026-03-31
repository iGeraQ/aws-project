package org.foundations.awsproject.services.student;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.foundations.awsproject.entities.Student;

import java.time.LocalDate;

public record CreateStudentResponse(
        Long id,
        @JsonProperty("nombres")
        String name,
        @JsonProperty("apellidos")
        String lastname,
        @JsonProperty("matricula")
        String studentId,
        @JsonProperty("promedio")
        float gpa,
        @JsonIgnore
        boolean isActive,
        @JsonIgnore
        LocalDate createdAt,
        @JsonIgnore
        LocalDate updatedAt
) {

    static CreateStudentResponse from(Student student){
        return new CreateStudentResponse(
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
