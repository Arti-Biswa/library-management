package com.java.librarymanagement.student.service;

import com.java.librarymanagement.student.mapper.StudentMapper;
import com.java.librarymanagement.student.model.Student;
import com.java.librarymanagement.student.model.StudentDTO;
import com.java.librarymanagement.student.repository.StudentRepository;
import com.java.librarymanagement.users.model.User;
import com.java.librarymanagement.users.service.UserService;
import com.java.librarymanagement.utils.exception.GlobalExceptionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserService userService;


    @Override
    public List<StudentDTO> findAll() {
        return List.of();
    }

    @Override
    public StudentDTO save(Student student) {
        Student savedStudent = studentRepository.save(student);
        return StudentMapper.toDTO(savedStudent);
    }

    @Override
    public StudentDTO fetchById(long id) throws Exception {
        return null;
    }

    @Override
    public String update(long id, StudentDTO studentDTO) {
        ensureAdminPermissions();

        // Find the student by ID
        Student student = findById(id);

        // Update only non-null fields from DTO
        if (studentDTO.getName() != null) student.setName(studentDTO.getName());
        if (studentDTO.getGrade() != null) student.setGrade(studentDTO.getGrade());
        if (studentDTO.getEmail() != null) student.setEmail(studentDTO.getEmail());
        if (studentDTO.getPassword() != null) student.setEmail(studentDTO.getPassword());
        if (studentDTO.getAddress() != null) student.setAddress(studentDTO.getAddress());
        if (studentDTO.getPhoneNumber() != null) student.setPhoneNumber(studentDTO.getPhoneNumber());
        if (studentDTO.getFaculty() != null) student.setFaculty(studentDTO.getFaculty());

        // Save the patched student
        studentRepository.save(student);
        return "Student information updated successfully.";
    }


    @Override
    public String deleteById(long id) {
        return "";
    }

    // Ensure only admins can perform certain operations (update or patch)
    private void ensureAdminPermissions() {
        User authenticatedUser = userService.fetchSelfInfo();
        if (Arrays.stream(authenticatedUser.getRoles().split(","))
                .noneMatch(role -> role.trim().equalsIgnoreCase("ADMIN"))) {
            throw new GlobalExceptionWrapper.ForbiddenException("Only admins can perform this operation.");
        }
    }

    // Find student by ID
    private Student findById(long id) {
        return studentRepository.findById(id).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException("Student not found with ID: " + id)
        );
    }
}
