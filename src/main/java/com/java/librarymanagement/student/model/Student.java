package com.java.librarymanagement.student.model;

import com.java.librarymanagement.utils.AuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@DynamicInsert
@DynamicUpdate
@Table(name = "student")
public class Student extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Student_SEQUENCE")
    @SequenceGenerator(name = "Student_SEQUENCE", sequenceName = "student_seq", allocationSize = 1)
    private long id;

    @Column(nullable = false)
    private String name;
                                                                                                
    @Column(nullable = false)
    private String grade;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Double phoneNumber;

    @Column(nullable = false)
    private String faculty;
}
