package org.foundations.awsproject.services.teacher;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.foundations.awsproject.entities.Teacher;

import java.time.LocalDate;

public record CreateTeacherResponse(
        Long id,
        @JsonProperty("nombres")
        String name,
        @JsonProperty("apellidos")
        String lastname,
        @JsonProperty("horasClase")
        int classHour,
        @JsonProperty("numeroEmpleado")
        String employeeId,
        @JsonIgnore
        boolean isActive,
        @JsonIgnore
        LocalDate createdAt,
        @JsonIgnore
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
