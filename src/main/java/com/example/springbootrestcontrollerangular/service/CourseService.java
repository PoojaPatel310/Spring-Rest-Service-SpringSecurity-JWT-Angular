package com.example.springbootrestcontrollerangular.service;

import com.example.springbootrestcontrollerangular.dto.CourseDto;
import com.example.springbootrestcontrollerangular.entity.Course;
import org.springframework.data.domain.Page;

public interface CourseService {

    Course loadCourseById(Long courseId);
    CourseDto createCourse(CourseDto courseDto);
    CourseDto updateCourse(CourseDto courseDto);
    Page<CourseDto> findCourseByCourseName(String keyword, int page, int size);
    void assignStudentToCourse(Long courseId, Long studentId);
    Page<CourseDto> fetchCourseForStudent(Long studentId, int page,int size);
    Page<CourseDto> fetchNonEnrolledInCourseForStudent(Long studentId, int page, int size);
    void  removeCourse(Long courseId);
    Page<CourseDto> fetchCoursesForInstructor(Long instructorId, int page, int size);
}
