package org.foundations.awsproject.services.teacher;

import jakarta.validation.constraints.NotBlank;

public record CreateTeacherRequest(
        @NotBlank String name,
        @NotBlank String lastname,
        int classHour,
        int employeeId
) {
}
