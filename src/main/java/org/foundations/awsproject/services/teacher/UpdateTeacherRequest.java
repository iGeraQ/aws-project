package org.foundations.awsproject.services.teacher;

public record UpdateTeacherRequest(
        String name,
        String lastname,
        int classHour,
        int employeeId
) { }
