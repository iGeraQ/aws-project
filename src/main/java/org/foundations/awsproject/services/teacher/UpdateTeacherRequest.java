package org.foundations.awsproject.services.teacher;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateTeacherRequest(
        @JsonProperty("nombres")
        @JsonAlias("name")
        @NotBlank
        String name,

        @JsonProperty("apellidos")
        @JsonAlias("lastname")
        @NotBlank
        String lastname,

        @JsonProperty("horasClase")
        @JsonAlias("classHour")
        @Positive
        int classHour,

        @JsonProperty("numeroEmpleado")
        @JsonAlias("employeeId")
        @NotBlank
        String employeeId
) { }
