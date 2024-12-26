package com.java.librarymanagement.student.controller;

import com.java.librarymanagement.student.mapper.StudentMapper;
import com.java.librarymanagement.student.model.Student;
import com.java.librarymanagement.student.model.StudentDTO;
import com.java.librarymanagement.student.repository.StudentRepository;
import com.java.librarymanagement.student.service.StudentServiceImpl;
import com.java.librarymanagement.utils.RestHelper;
import com.java.librarymanagement.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    @Autowired
    private StudentServiceImpl studentServiceImpl;

    @Autowired
    private StudentRepository studentRepository;

    /**
     * Adding up the new student by admin.
     *
     * @param studentDTO The entity to be saved.
     * @return The saved entity.
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> save(@Validated @RequestBody StudentDTO studentDTO) {
        Student student = StudentMapper.toEntity(studentDTO);
        StudentDTO savedStudentDTO = studentServiceImpl.save(student);
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("student", savedStudentDTO);
        return RestHelper.responseSuccess(responseMap);
    }

    /**
     * Fetches all the user entities in the system.
     *
     * @return The list of user entities.
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<StudentDTO> findAll() {
        // Fetch all student entities from the repository
        List<Student> students = studentRepository.findAll();

        // Map the entities to DTOs and return the list
        return students.stream()
                .map(StudentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/count")
    public long getTotalStudent() {
        return studentRepository.count();
    }

    /**
     * Updates the existing student entity.
     *
     * @param id The updated student entity.
     * @return The message indicating the confirmation on updated student entity.
     */
    @PatchMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> update(@PathVariable long id, @RequestBody @Validated StudentDTO studentDTO) {
        String message = studentServiceImpl.update(id, studentDTO);
        return RestHelper.responseMessage(message);
    }
}
