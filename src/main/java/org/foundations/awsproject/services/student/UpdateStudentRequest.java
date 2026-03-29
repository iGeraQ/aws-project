package org.foundations.awsproject.services.student;

public record UpdateStudentRequest(
        String name,
        String lastname,
        String studentId,
        float gpa
) { }
