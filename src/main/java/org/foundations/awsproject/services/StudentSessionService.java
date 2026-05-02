package org.foundations.awsproject.services;

import org.foundations.awsproject.entities.Student;
import org.foundations.awsproject.entities.StudentSession;
import org.foundations.awsproject.repository.session.DynamoDBSessionRepository;
import org.foundations.awsproject.repository.student.IStudentRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentSessionService {

    private final IStudentRepository studentRepository;
    private final DynamoDBSessionRepository sessionRepository;
    private final SecureRandom random = new SecureRandom();

    public StudentSessionService(IStudentRepository studentRepository, DynamoDBSessionRepository sessionRepository) {
        this.studentRepository = studentRepository;
        this.sessionRepository = sessionRepository;
    }

    public StudentSession login(Long studentId, String password) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        
        if (studentOpt.isEmpty()) {
            throw new IllegalArgumentException("Alumno no encontrado");
        }
        
        Student student = studentOpt.get();
        if (student.getPassword() == null || !BCrypt.checkpw(password, student.getPassword())) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }
        
        StudentSession session = new StudentSession();
        session.setId(UUID.randomUUID().toString());
        session.setAlumnoId(studentId);
        session.setFecha(Instant.now().getEpochSecond());
        session.setActive(true);
        session.setSessionString(generateSessionString());
        
        sessionRepository.save(session);
        return session;
    }

    public boolean verify(String sessionString) {
        Optional<StudentSession> sessionOpt = sessionRepository.findBySessionString(sessionString);
        return sessionOpt.isPresent() && sessionOpt.get().isActive();
    }

    public void logout(String sessionString) {
        Optional<StudentSession> sessionOpt = sessionRepository.findBySessionString(sessionString);
        if (sessionOpt.isPresent()) {
            StudentSession session = sessionOpt.get();
            session.setActive(false);
            sessionRepository.update(session);
        }
    }

    private String generateSessionString() {
        StringBuilder sb = new StringBuilder(128);
        for (int i = 0; i < 128; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
