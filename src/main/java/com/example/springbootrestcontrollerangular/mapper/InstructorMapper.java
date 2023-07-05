package com.example.springbootrestcontrollerangular.mapper;

import com.example.springbootrestcontrollerangular.dto.InstructorDto;
import com.example.springbootrestcontrollerangular.entity.Instructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class InstructorMapper {

    public InstructorDto fromInstructor(Instructor instructor){
        InstructorDto instructorDto = new InstructorDto();
        BeanUtils.copyProperties(instructor,instructorDto);
        return instructorDto;
    }

    public Instructor fromInstructorDto(InstructorDto instructorDto){
        Instructor instructor = new Instructor();
        BeanUtils.copyProperties(instructorDto,instructor);
        return instructor;
    }
}
