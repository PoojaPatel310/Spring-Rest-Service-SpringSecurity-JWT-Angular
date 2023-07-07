package com.example.springbootrestcontrollerangular.service;

import com.example.springbootrestcontrollerangular.dao.InstructorDao;
import com.example.springbootrestcontrollerangular.dto.InstructorDto;
import com.example.springbootrestcontrollerangular.entity.Instructor;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InstructorService {

    Instructor loadInstructorById(Long instructorId);

    Page<InstructorDto> findInstructorByName(String name, int page, int size);

    InstructorDto loadInstructorByEmail(String email);

    InstructorDto createInstructor(InstructorDto instructorDto);

    InstructorDto updateInstructor(InstructorDto instructorDto);

    List<InstructorDto> fetchInstructor();

    void removeInstructor(Long instructorId);
}
