package com.example.springbootrestcontrollerangular.web;

import com.example.springbootrestcontrollerangular.dto.CourseDto;
import com.example.springbootrestcontrollerangular.dto.InstructorDto;
import com.example.springbootrestcontrollerangular.entity.User;
import com.example.springbootrestcontrollerangular.service.CourseService;
import com.example.springbootrestcontrollerangular.service.InstructorService;
import com.example.springbootrestcontrollerangular.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructors")
public class InstructorRestController {

    private InstructorService instructorService;

    private UserService userService;

    private CourseService courseService;

    public InstructorRestController(InstructorService instructorService, UserService userService, CourseService courseService) {
        this.instructorService = instructorService;
        this.userService = userService;
        this.courseService = courseService;
    }


    @GetMapping
        public Page<InstructorDto> searchInstructors(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                                     @RequestParam(name = "Page", defaultValue = "0") int page,
                                                     @RequestParam(name = "size", defaultValue = "5") int size){
            return instructorService.findInstructorByName(keyword,page,size);
        }

        @GetMapping("/all")
        public List<InstructorDto> findAllInstructor(){
            return instructorService.fetchInstructor();
        }

        @DeleteMapping("/{instructorId}")
        public void deleteInstructor(@PathVariable Long instructorId){
            instructorService.removeInstructor(instructorId);
        }


        @PostMapping
        public InstructorDto saveInstructor(@RequestBody InstructorDto instructorDto){
            User user = userService.loadUserByEmail(instructorDto.getUser().getEmail());
            if (user != null) throw new RuntimeException("Email Already Exist");
            return instructorService.createInstructor(instructorDto);
        }

        @PutMapping("/{instructorId}")
        public InstructorDto updateInstructor(@RequestBody InstructorDto instructorDto, @PathVariable Long instructorId){
            instructorDto.setInstructorId(instructorId);
            return instructorService.updateInstructor(instructorDto);
        }

        @GetMapping("/{instructorId}/courses")
        public Page<CourseDto> courseByInstructorId(@PathVariable Long instructorId,
                                                    @RequestParam(name = "page",defaultValue = "0") int page,
                                                    @RequestParam(name = "size",defaultValue = "5") int size){
            return courseService.fetchCoursesForInstructor(instructorId,page,size);

        }

        @GetMapping("/find")
        public InstructorDto loadInstructorByEmail(@RequestParam(name = "email",defaultValue = "")String email){
            return instructorService.loadInstructorByEmail(email);
        }





}
