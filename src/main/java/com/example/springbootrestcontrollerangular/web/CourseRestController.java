package com.example.springbootrestcontrollerangular.web;

import com.example.springbootrestcontrollerangular.dto.CourseDto;
import com.example.springbootrestcontrollerangular.service.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseRestController {

    private CourseService courseService;

    public CourseRestController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public Page<CourseDto> searchCourses(@RequestParam(name = "keyword",defaultValue = "") String keyword,
                                         @RequestParam(name = "page",defaultValue = "0") int page,
                                         @RequestParam(name = "size",defaultValue = "5") int size){
        return courseService.findCourseByCourseName(keyword,page,size);
    }

    @DeleteMapping("/{courseId}")
    public void deleteCourse(@PathVariable Long courseId){
        courseService.removeCourse(courseId);
    }

    @PostMapping
    public CourseDto saveCourse(@RequestBody CourseDto courseDto){
        return courseService.createCourse(courseDto);
    }

    @PutMapping("/{courseId}")
    public CourseDto updateCourse(@RequestBody CourseDto courseDto, @PathVariable Long courseId){
        courseDto.setCourseId(courseId);
        return courseService.updateCourse(courseDto);
    }

    @PostMapping("/{courseId}/enroll/students/{studentId}")
    public void enrollStudentInCourse(@PathVariable Long courseId,@PathVariable Long studentId){
        courseService.assignStudentToCourse(courseId,studentId);
    }
}
