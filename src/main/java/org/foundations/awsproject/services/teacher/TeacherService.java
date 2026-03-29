package org.foundations.awsproject.services.teacher;

import org.foundations.awsproject.entities.Teacher;
import org.foundations.awsproject.repository.teacher.ITeacherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class TeacherService {

    private final ITeacherRepository teacherRepository;

    public TeacherService(ITeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<UpdateTeacherResponse> findAll() {
        return teacherRepository.findAll().stream()
                .map(UpdateTeacherResponse::from)
                .toList();
    }

    public UpdateTeacherResponse findById(UUID id) {
        return teacherRepository.findById(id)
                .map(UpdateTeacherResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher not found"));
    }

    @Transactional
    public CreateTeacherResponse create(CreateTeacherRequest request) {
        Teacher teacher = new Teacher();
        teacher.setName(request.name());
        teacher.setLastname(request.lastname());
        teacher.setClassHour(request.classHour());
        teacher.setEmployeeId(request.employeeId());

        Teacher savedTeacher = teacherRepository.save(teacher);
        return CreateTeacherResponse.from(savedTeacher);
    }

    @Transactional
    public UpdateTeacherResponse update(UUID id, UpdateTeacherRequest request) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher not found"));

        if (request.name() != null) {
            teacher.setName(request.name());
        }
        if (request.lastname() != null) {
            teacher.setLastname(request.lastname());
        }
        teacher.setClassHour(request.classHour());
        teacher.setEmployeeId(request.employeeId());

        Teacher updatedTeacher = teacherRepository.save(teacher);
        return UpdateTeacherResponse.from(updatedTeacher);
    }

    @Transactional
    public UpdateTeacherResponse delete(UUID id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher not found"));

        teacher.deactivate();
        Teacher deletedTeacher = teacherRepository.save(teacher);
        return UpdateTeacherResponse.from(deletedTeacher);
    }
}
