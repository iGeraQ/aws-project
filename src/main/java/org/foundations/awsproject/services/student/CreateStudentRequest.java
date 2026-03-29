package org.foundations.awsproject.services.student;
import jakarta.validation.constraints.NotBlank;

public record CreateStudentRequest(
        @NotBlank String name,
        @NotBlank String lastname,
        @NotBlank String studentId,
        @NotBlank float gpa
) {
}
