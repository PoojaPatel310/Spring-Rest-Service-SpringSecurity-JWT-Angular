package com.example.springbootrestcontrollerangular.runner;

import com.example.springbootrestcontrollerangular.dto.CourseDto;
import com.example.springbootrestcontrollerangular.dto.InstructorDto;
import com.example.springbootrestcontrollerangular.dto.StudentDto;
import com.example.springbootrestcontrollerangular.dto.UserDto;
import com.example.springbootrestcontrollerangular.entity.Student;
import com.example.springbootrestcontrollerangular.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MyRunner implements CommandLineRunner {

    private RoleService roleService;

    private UserService userService;

    private InstructorService instructorService;

    private CourseService courseService;

    private StudentService studentService;

    public MyRunner(RoleService roleService, UserService userService, InstructorService instructorService, CourseService courseService, StudentService studentService) {
        this.roleService = roleService;
        this.userService = userService;
        this.instructorService = instructorService;
        this.courseService = courseService;
        this.studentService = studentService;
    }


    @Override
    public void run(String... args) throws Exception {
        createRoles();
        createAdmin();
        createInstructors();
        createCourses();
        StudentDto student = createStudent();
        assignCourseTOStudent(student);
        createStudents();
    }

    private void createStudents() {
        for (int i=1; i<10 ; i++){
            StudentDto studentDto = new StudentDto();
            studentDto.setFirstName("studentFN" + i);
            studentDto.setLastName("studentLN" + i);
            studentDto.setLevel("intermediate" + i);
            UserDto userDto = new UserDto();
            userDto.setEmail("student"+ i+"@gmail.com");
            userDto.setPassword("1234");
            studentDto.setUser(userDto);
            studentService.createStudent(studentDto);
        }
    }

    private void assignCourseTOStudent(StudentDto student) {
        courseService.assignStudentToCourse(1L,student.getStudentId());
    }

    private StudentDto createStudent() {
        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName("studentFN");
        studentDto.setLastName("studentLN");
        studentDto.setLevel("intermediate");
        UserDto userDto = new UserDto();
        userDto.setEmail("student@gmail.com");
        userDto.setPassword("1234");
        studentDto.setUser(userDto);
        return studentService.createStudent(studentDto);
    }

    private void createCourses() {
        for (int i =0 ;i<20; i++){
            CourseDto courseDto = new CourseDto();
            courseDto.setCourseDescription("Java"+i);
            courseDto.setCourseDuration(i+"Hours");
            courseDto.setCourseName("Java Courses"+i);
            InstructorDto instructorDto = new InstructorDto();
            instructorDto.setInstructorId(1L);
            courseDto.setInstructor(instructorDto);
            courseService.createCourse(courseDto);
        }
    }

    private void createInstructors() {
        for(int i =0; i<10; i++){
            InstructorDto instructorDto = new InstructorDto();
            instructorDto.setFirstName("instructor" +i+ "FN");
            instructorDto.setLastName("instructor" +i+ "LN");
            instructorDto.setSummary("master" +i);
            UserDto userDto = new UserDto();
            userDto.setEmail("instructor"+i+"@gmail.com");
            userDto.setPassword("1234");
            instructorDto.setUser(userDto);
            instructorService.createInstructor(instructorDto);
        }
    }

    private void createAdmin() {
        userService.createUser("admin@gmail.com","1234");
        userService.assignRoleToUser("admin@gmail.com","Admin");
    }

    private void createRoles() {
        Arrays.asList("Admin","Instructor","Student").forEach(role-> roleService.createRole(role));
    }

}














