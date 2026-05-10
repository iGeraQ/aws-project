package org.foundations.awsproject.services.student;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateStudentRequest(
        @JsonProperty("nombres")
        @JsonAlias("name")
        @NotBlank String name,

        @JsonProperty("apellidos")
        @JsonAlias("lastname")
        @NotBlank String lastname,

        @JsonProperty("matricula")
        @JsonAlias("studentId")
        @NotBlank String studentId,

        @JsonProperty("password")
        @JsonAlias("password")
        @NotBlank String password,

        @JsonProperty("promedio")
        @JsonAlias("gpa")
        @Positive
        @Max(10)
        float gpa
) { }
