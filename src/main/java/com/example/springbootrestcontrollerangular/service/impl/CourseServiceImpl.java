package com.example.springbootrestcontrollerangular.service.impl;

import com.example.springbootrestcontrollerangular.dao.CourseDao;
import com.example.springbootrestcontrollerangular.dao.InstructorDao;
import com.example.springbootrestcontrollerangular.dao.StudentDao;
import com.example.springbootrestcontrollerangular.dto.CourseDto;
import com.example.springbootrestcontrollerangular.entity.Course;
import com.example.springbootrestcontrollerangular.entity.Instructor;
import com.example.springbootrestcontrollerangular.entity.Student;
import com.example.springbootrestcontrollerangular.mapper.CourseMapper;
import com.example.springbootrestcontrollerangular.service.CourseService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private CourseDao courseDao;

    private CourseMapper courseMapper;

    private InstructorDao instructorDao;

    private StudentDao studentDao;

    public CourseServiceImpl(CourseDao courseDao, CourseMapper courseMapper, InstructorDao instructorDao, StudentDao studentDao) {
        this.courseDao = courseDao;
        this.courseMapper = courseMapper;
        this.instructorDao = instructorDao;
        this.studentDao = studentDao;
    }

    @Override
    public Course loadCourseById(Long courseId) {
        return courseDao.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course with Id "+courseId+" not Found"));
    }

    @Override
    public CourseDto createCourse(CourseDto courseDto) {
        Course course = courseMapper.fromCourseDto(courseDto);
        Instructor instructor = instructorDao.findById(courseDto.getInstructor().getInstructorId()).orElseThrow(() -> new EntityNotFoundException("Instructor with Id "+courseDto.getInstructor().getInstructorId()+" not Found"));
        course.setInstructor(instructor);
        Course savedCourse = courseDao.save(course);
        return  courseMapper.fromCourse(savedCourse);
    }

    @Override
    public CourseDto updateCourse(CourseDto courseDto) {
        Course loadCourse = loadCourseById(courseDto.getCourseId());
        Instructor instructor = instructorDao.findById(courseDto.getInstructor().getInstructorId()).orElseThrow(() -> new EntityNotFoundException("Instructor with Id "+courseDto.getInstructor().getInstructorId()+" not Found"));
        Course course = courseMapper.fromCourseDto(courseDto);
        course.setInstructor(instructor);
        course.setStudents(loadCourse.getStudents());
        Course updatedCourse = courseDao.save(course);
        return courseMapper.fromCourse(updatedCourse);
    }

    @Override
    public Page<CourseDto> findCourseByCourseName(String keyword, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<Course> coursePage = courseDao.findCourseByCourseNameContains(keyword,pageRequest);
        return new PageImpl<>(coursePage.getContent().stream().map(course -> courseMapper.fromCourse(course)).collect(Collectors.toList()),pageRequest, coursePage.getTotalElements());
    }

    @Override
    public void assignStudentToCourse(Long courseId, Long studentId) {
        Student student = studentDao.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student with Id "+studentId+" not Found"));
        Course  course = loadCourseById(courseId);
        course.assignStudentToCourse(student);
    }

    @Override
    public Page<CourseDto> fetchCourseForStudent(Long studentId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<Course> studentCoursePage = courseDao.getCourseByStudentId(studentId,pageRequest);
        return new PageImpl<>(studentCoursePage.getContent().stream().map(course -> courseMapper.fromCourse(course)).collect(Collectors.toList()),pageRequest, studentCoursePage.getTotalElements());

    }

    @Override
    public Page<CourseDto> fetchNonEnrolledInCourseForStudent(Long studentId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<Course> nonEnrolledInCoursesPage = courseDao.getNonEnrolledInCourseByStudentId(studentId,pageRequest);
        return new PageImpl<>(nonEnrolledInCoursesPage.getContent().stream().map(course -> courseMapper.fromCourse(course)).collect(Collectors.toList()),pageRequest, nonEnrolledInCoursesPage.getTotalElements());

    }

    @Override
    public void removeCourse(Long courseId) {
        courseDao.deleteById(courseId);
    }

    @Override
    public Page<CourseDto> fetchCoursesForInstructor(Long instructorId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<Course> instructorCoursesPage = courseDao.getCoursesByInstructorId(instructorId,pageRequest);
        return new PageImpl<>(instructorCoursesPage.getContent().stream().map(course -> courseMapper.fromCourse(course)).collect(Collectors.toList()),pageRequest, instructorCoursesPage.getTotalElements());

    }
}
