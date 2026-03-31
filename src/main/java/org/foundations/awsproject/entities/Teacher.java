package org.foundations.awsproject.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "teachers")
@Data // Genera Getters, Setters, toString, etc.
@NoArgsConstructor // Constructor vacío para JPA
@AllArgsConstructor // Constructor con todos los campos
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;
    private int classHour;

    @Column(name = "employee_id", unique = true) // Recomendado: nombre claro en BD y que sea único
    private String employeeId; // Permite ids alfanuméricos del cliente

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
