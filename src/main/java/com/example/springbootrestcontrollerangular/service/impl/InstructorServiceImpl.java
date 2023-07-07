package com.example.springbootrestcontrollerangular.service.impl;

import com.example.springbootrestcontrollerangular.dao.InstructorDao;
import com.example.springbootrestcontrollerangular.dto.InstructorDto;
import com.example.springbootrestcontrollerangular.entity.Course;
import com.example.springbootrestcontrollerangular.entity.Instructor;
import com.example.springbootrestcontrollerangular.entity.User;
import com.example.springbootrestcontrollerangular.mapper.InstructorMapper;
import com.example.springbootrestcontrollerangular.service.CourseService;
import com.example.springbootrestcontrollerangular.service.InstructorService;
import com.example.springbootrestcontrollerangular.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class InstructorServiceImpl implements InstructorService {

    private InstructorDao instructorDao;
    private InstructorMapper instructorMapper;
    private UserService userService;
    private CourseService courseService;

    public InstructorServiceImpl(InstructorDao instructorDao, InstructorMapper instructorMapper, UserService userService, CourseService courseService) {
        this.instructorDao = instructorDao;
        this.instructorMapper = instructorMapper;
        this.userService = userService;
        this.courseService = courseService;
    }


    @Override
    public Instructor loadInstructorById(Long instructorId) {
        return instructorDao.findById(instructorId).orElseThrow(() -> new EntityNotFoundException("Instructor with Id " +instructorId+ " not Found"));
    }

    @Override
    public Page<InstructorDto> findInstructorByName(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<Instructor> instructorPage = instructorDao.findInstructorByName(name,pageRequest);
        return new PageImpl<>(instructorPage.getContent().stream().map(instructor -> instructorMapper.fromInstructor(instructor)).collect(Collectors.toList()), pageRequest, instructorPage.getTotalElements());
    }

    @Override
    public InstructorDto loadInstructorByEmail(String email) {
       return instructorMapper.fromInstructor(instructorDao.findInstructorByEmail(email));
    }

    @Override
    public InstructorDto createInstructor(InstructorDto instructorDto) {
        User user = userService.createUser(instructorDto.getUser().getEmail(), instructorDto.getUser().getPassword());
        userService.assignRoleToUser(user.getEmail(),"Instructor");
        Instructor instructor = instructorMapper.fromInstructorDto(instructorDto);
        Instructor savedInstructor = instructorDao.save(instructor);
        return instructorMapper.fromInstructor(savedInstructor);
    }

    @Override
    public InstructorDto updateInstructor(InstructorDto instructorDto) {
        Instructor loadInstructor = loadInstructorById(instructorDto.getInstructorId());
        Instructor instructor = instructorMapper.fromInstructorDto(instructorDto);
        instructor.setUser(loadInstructor.getUser());
        instructor.setCourses(loadInstructor.getCourses());
        Instructor updatedInstructor = instructorDao.save(instructor);
        return instructorMapper.fromInstructor(updatedInstructor);
    }

    @Override
    public List<InstructorDto> fetchInstructor() {
        return instructorDao.findAll().stream().map(instructor -> instructorMapper.fromInstructor(instructor)).collect(Collectors.toList());
    }

    @Override
    public void removeInstructor(Long instructorId) {
        Instructor instructor = loadInstructorById(instructorId);
        for (Course course : instructor.getCourses()) {
            courseService.removeCourse(course.getCourseId());
        }
        instructorDao.deleteById(instructorId);
    }
}
