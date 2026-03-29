package org.foundations.awsproject.services.teacher;

import org.foundations.awsproject.entities.Teacher;

import java.time.LocalDate;
import java.util.UUID;

public record CreateTeacherResponse(
        UUID id,
        String name,
        String lastname,
        int classHour,
        int employeeId,
        boolean isActive,
        LocalDate createdAt,
        LocalDate updatedAt
) {

    static CreateTeacherResponse from(Teacher teacher){
        return new CreateTeacherResponse(
                teacher.getId(),
                teacher.getName(),
                teacher.getLastname(),
                teacher.getClassHour(),
                teacher.getEmployeeId(),
                teacher.isActive(),
                teacher.getCreatedAt(),
                teacher.getUpdatedAt()

        );
    }
}
