package com.example.springbootrestcontrollerangular.web;

import com.example.springbootrestcontrollerangular.dto.CourseDto;
import com.example.springbootrestcontrollerangular.dto.StudentDto;
import com.example.springbootrestcontrollerangular.entity.User;
import com.example.springbootrestcontrollerangular.service.CourseService;
import com.example.springbootrestcontrollerangular.service.StudentService;
import com.example.springbootrestcontrollerangular.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("students")
public class StudentRestController {

    private StudentService studentService;

    private UserService userService;

    private CourseService courseService;

    public StudentRestController(StudentService studentService, UserService userService, CourseService courseService) {
        this.studentService = studentService;
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping
    public Page<StudentDto> searchStudents(@RequestParam(name = "keyword",defaultValue = "") String keyword,
                                           @RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(name = "size", defaultValue = "5") int size){
        return studentService.loadStudentByName(keyword,page,size);
    }

    @DeleteMapping("/{studentId}")
    public void deleteStudent(@PathVariable Long studentId){
        studentService.removeStudent(studentId);
    }

    @PostMapping
    public StudentDto saveStudent(@RequestBody StudentDto studentDto) {
        User user = userService.loadUserByEmail(studentDto.getUser().getEmail());
        if (user != null) throw new RuntimeException("Email Already Exist");
        return studentService.createStudent(studentDto);
    }

    @PutMapping("/{studentId}")
    public StudentDto updateStudent(@RequestBody StudentDto studentDto,@PathVariable Long studentId){
        studentDto.setStudentId(studentId);
        return studentService.updateStudent(studentDto);
    }

    @GetMapping("/{studentId}/courses")
    public Page<CourseDto> courseByStudentId(@PathVariable Long studentId,
                                             @RequestParam(name = "page",defaultValue = "0") int page,
                                             @RequestParam(name = "size",defaultValue = "5") int size){
        return courseService.fetchCourseForStudent(studentId,page,size);
    }

    @GetMapping("/{studentId}/other-courses")
    public Page<CourseDto> nonSubscribedCoursesByStudentId(@PathVariable Long studentId,
                                                           @RequestParam(name = "page", defaultValue = "0") int page,
                                                           @RequestParam(name = "size", defaultValue = "5") int size){
        return courseService.fetchNonEnrolledInCourseForStudent(studentId,page,size);
    }

    @GetMapping("/find")
    public StudentDto loadStudentByEmail(@RequestParam(name = "email",defaultValue = "") String email) {
        return studentService.loadStudentByEmail(email);
    }










}
