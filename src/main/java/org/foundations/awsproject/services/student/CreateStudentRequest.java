package org.foundations.awsproject.services.student;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateStudentRequest(
        @Positive
        Long id,
        @NotBlank
        @JsonProperty("nombres")
        @JsonAlias("name")
        String name,
        @NotBlank
        @JsonProperty("apellidos")
        @JsonAlias("lastname")
        String lastname,
        @NotBlank
        @JsonProperty("matricula")
        @JsonAlias("studentId")
        String studentId,
        @NotNull
        @Positive
        @Max(10)
        @JsonProperty("promedio")
        @JsonAlias("gpa")
        float gpa,
        @NotBlank
        @JsonProperty("password")
        @JsonAlias("password")
        String password
) {     
}
