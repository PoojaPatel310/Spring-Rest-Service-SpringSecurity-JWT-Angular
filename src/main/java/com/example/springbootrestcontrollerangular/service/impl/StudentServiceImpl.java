package com.example.springbootrestcontrollerangular.service.impl;

import com.example.springbootrestcontrollerangular.dao.StudentDao;
import com.example.springbootrestcontrollerangular.dto.StudentDto;
import com.example.springbootrestcontrollerangular.entity.Course;
import com.example.springbootrestcontrollerangular.entity.Student;
import com.example.springbootrestcontrollerangular.entity.User;
import com.example.springbootrestcontrollerangular.mapper.StudentMapper;
import com.example.springbootrestcontrollerangular.service.StudentService;
import com.example.springbootrestcontrollerangular.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private StudentDao studentDao;

    private StudentMapper studentMapper;
    private UserService userService;

    public StudentServiceImpl(StudentDao studentDao, StudentMapper studentMapper, UserService userService) {
        this.studentDao = studentDao;
        this.studentMapper = studentMapper;
        this.userService = userService;
    }

    @Override
    public Student loadStudentById(Long studentId) {
        return studentDao.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student with Id " +studentId+ " not Found"));
    }

    @Override
    public Page<StudentDto> loadStudentByName(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
       Page<Student> studentPage = studentDao.findStudentsByName(name, pageRequest);
       return new PageImpl<>(studentPage.getContent().stream().map(student -> studentMapper.fromStudent(student)).collect(Collectors.toList()),pageRequest,studentPage.getTotalElements());
    }

    @Override
    public StudentDto loadStudentByEmail(String email) {
        return studentMapper.fromStudent(studentDao.findStudentByEmail(email));
    }

    @Override
    public StudentDto createStudent(StudentDto studentDto) {
        User user = userService.createUser(studentDto.getUser().getEmail(),studentDto.getUser().getPassword());
        userService.assignRoleToUser(user.getEmail(),"Student");
        Student student = studentMapper.fromStudentDto(studentDto);
        student.setUser(user);
        Student savedStudent = studentDao.save(student);
        return studentMapper.fromStudent(savedStudent);
    }

    @Override
    public StudentDto updateStudent(StudentDto studentDto) {
        Student loadedStudent = loadStudentById(studentDto.getStudentId());
        Student student = studentMapper.fromStudentDto(studentDto);
        student.setUser(loadedStudent.getUser());
        student.setCourses(loadedStudent.getCourses());
        Student updatedStudent = studentDao.save(student);
        return studentMapper.fromStudent(updatedStudent);
    }

    @Override
    public void removeStudent(Long studentId) {
        Student student = loadStudentById(studentId);
        Iterator<Course> courseIterator = student.getCourses().iterator();
        if(courseIterator.hasNext()) {
            Course course = courseIterator.next();
            course.removeStudentFromCourse(student);
        }
        studentDao.deleteById(studentId);
    }
}
