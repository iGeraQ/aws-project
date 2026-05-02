package org.foundations.awsproject.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "students")
@Data // Genera automáticamente: Getters, Setters, toString, equals y hashCode
@NoArgsConstructor // Genera el constructor vacío (obligatorio para JPA)
@AllArgsConstructor // Genera un constructor con todos los atributos (opcional, muy útil)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;
    private String studentId;
    private float gpa;
    private String password;
    private String fotoPerfilUrl;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(nullable = false, updatable = false)
    private LocalDate createdAt = LocalDate.now();

    @Column(nullable = false)
    private LocalDate updatedAt = LocalDate.now();

    @PrePersist
    private void prePersist() {
        LocalDate now = LocalDate.now();
        if (createdAt == null) {
            createdAt = now;
        }
        updatedAt = now;
    }

    @PreUpdate
    private void preUpdate() {
        updatedAt = LocalDate.now();
    }

    public void deactivate() {
        this.isActive = false;
    }
}
