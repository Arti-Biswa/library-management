package com.java.librarymanagement.student.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private long id;
    private String name;
    private String grade;
    private String email;
    private String password;
    private String address;
    private Double phoneNumber;
    private String faculty;

}
