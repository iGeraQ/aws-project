package org.foundations.awsproject.services.teacher;

import org.foundations.awsproject.entities.Teacher;
import org.foundations.awsproject.repository.teacher.ITeacherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
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

    public UpdateTeacherResponse findById(Long id) {
        return teacherRepository.findById(id)
                .map(UpdateTeacherResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher not found"));
    }

    public CreateTeacherResponse create(CreateTeacherRequest request) {
        Teacher teacher = new Teacher();
        teacher.setId(request.id());
        teacher.setName(request.name());
        teacher.setLastname(request.lastname());
        teacher.setClassHour(request.classHour());
        teacher.setEmployeeId(request.employeeId());

        Teacher savedTeacher = teacherRepository.save(teacher);
        return CreateTeacherResponse.from(savedTeacher);
    }

    public UpdateTeacherResponse update(Long id, UpdateTeacherRequest request) {
        Teacher teacher = new Teacher();
        teacher.setName(request.name());
        teacher.setLastname(request.lastname());
        teacher.setClassHour(request.classHour());
        teacher.setEmployeeId(request.employeeId());

        return teacherRepository.update(id, teacher)
                .map(UpdateTeacherResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher not found"));
    }

    public UpdateTeacherResponse delete(Long id) {
        return teacherRepository.delete(id)
                .map(UpdateTeacherResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher not found"));
    }
}
