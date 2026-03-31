package org.foundations.awsproject.services.student;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateStudentRequest(
        @NotBlank String name,
        @NotBlank String lastname,
        @NotBlank String studentId,
        @NotNull @Positive @Max(4) float gpa
) {
}
