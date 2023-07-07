package com.example.springbootrestcontrollerangular.service;

import com.example.springbootrestcontrollerangular.dto.StudentDto;
import com.example.springbootrestcontrollerangular.entity.Student;
import org.springframework.data.domain.Page;

public interface StudentService {

    Student loadStudentById(Long studentId);

    Page<StudentDto> loadStudentByName(String name, int page, int size);

    StudentDto loadStudentByEmail(String email);

    StudentDto createStudent(StudentDto studentDto);

    StudentDto updateStudent(StudentDto studentDto);

    void removeStudent(Long studentId);
}
