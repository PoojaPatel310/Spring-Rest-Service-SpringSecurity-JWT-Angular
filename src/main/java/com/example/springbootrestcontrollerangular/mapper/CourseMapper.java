package com.example.springbootrestcontrollerangular.mapper;

import com.example.springbootrestcontrollerangular.dto.CourseDto;
import com.example.springbootrestcontrollerangular.entity.Course;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CourseMapper {

    private InstructorMapper instructorMapper;

    public CourseMapper(InstructorMapper instructorMapper) {
        this.instructorMapper = instructorMapper;
    }

    public CourseDto fromCourse(Course course){
        CourseDto courseDto = new CourseDto();
        BeanUtils.copyProperties(course,courseDto);
        courseDto.setInstructor(instructorMapper.fromInstructor(course.getInstructor()));
        return courseDto;
    }

    public Course fromCourseDto(CourseDto courseDto){
        Course course = new Course();
        BeanUtils.copyProperties(courseDto,course);
        return course;
    }
}
