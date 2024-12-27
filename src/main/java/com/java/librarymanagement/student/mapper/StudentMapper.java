package com.java.librarymanagement.student.mapper;

import com.java.librarymanagement.student.model.Student;
import com.java.librarymanagement.student.model.StudentDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class StudentMapper {

    /**
     * Converts a Student entity to a StudentDTO.
     *
     * @param student The student entity.
     * @return The StudentDTO.
     */
    public static StudentDTO toDTO(Student student) {
        if (student == null) {
            return null;
        }
        StudentDTO dto = new StudentDTO();
        BeanUtils.copyProperties(student, dto);

        // Custom mapping for phoneNumber if it's a Double in DTO
        if (student.getPhoneNumber() != null) {
            dto.setPhoneNumber(student.getPhoneNumber());
        }

        return dto;
    }

    /**
     * Converts a list of Student entities to a list of StudentDTOs.
     *
     * @param students The list of student entities.
     * @return The list of StudentDTOs.
     */
    public static List<StudentDTO> toDTO(List<Student> students) {
        return students.stream()
                .map(StudentMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converts an Optional Student entity to an Optional StudentDTO.
     *
     * @param student The optional student entity.
     * @return The optional StudentDTO.
     */
    public static Optional<StudentDTO> toDTO(Optional<Student> student) {
        return student.map(StudentMapper::toDTO);
    }

    /**
     * Converts a StudentDTO to a Student entity.
     *
     * @param dto The StudentDTO.
     * @return The Student entity.
     */
    public static Student toEntity(StudentDTO dto) {
        if (dto == null) {
            return null;
        }
        Student student = new Student();
        BeanUtils.copyProperties(dto, student);

        // Custom mapping for phoneNumber (no conversion needed if it's Double)
        if (dto.getPhoneNumber() != null) {
            student.setPhoneNumber(dto.getPhoneNumber());  // Directly set as Double
        }

        return student;
    }
}
