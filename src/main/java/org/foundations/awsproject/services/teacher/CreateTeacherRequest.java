package org.foundations.awsproject.services.teacher;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateTeacherRequest(
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
        @JsonProperty("horasClase")
        @JsonAlias("classHour")
        int classHour,
        @NotBlank
        @JsonProperty("numeroEmpleado")
        @JsonAlias("employeeId")
        String employeeId
) {
}
