package org.foundations.awsproject.services;

import org.foundations.awsproject.services.student.UpdateStudentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@Service
public class SnsService {

    private final SnsClient snsClient;

    @Value("${AWS_SNS_TOPIC_ARN}")
    private String topicArn;

    public SnsService(SnsClient snsClient) {
        this.snsClient = snsClient;
    }

    public void sendStudentEmail(UpdateStudentResponse student) {
        String subject = "Boleta de Calificaciones: " + student.name() + " " + student.lastname();

        String message = buildSchoolReport(student);

        PublishRequest request = PublishRequest.builder()
                .topicArn(topicArn)
                .subject(subject)
                .message(message)
                .build();

        snsClient.publish(request);
    }

    private String buildSchoolReport(UpdateStudentResponse student) {
        return "==========================================\n" +
               "           REPORTE ACADÉMICO              \n" +
               "==========================================\n\n" +
               "Estimado/a,\n\n" +
               "Adjunto encontrará la información y \n" +
               "las calificaciones actuales del alumno:\n\n" +
               "------------------------------------------\n" +
               " ALUMNO: " + student.name() + " " + student.lastname() + "\n" +
               " MATRÍCULA: " + student.studentId() + "\n" +
               " PROMEDIO (GPA): " + student.gpa() + "\n" +
               "------------------------------------------\n\n" +
               "Saludos cordiales,\n" +
               "Servicios Escolares";
    }
}
