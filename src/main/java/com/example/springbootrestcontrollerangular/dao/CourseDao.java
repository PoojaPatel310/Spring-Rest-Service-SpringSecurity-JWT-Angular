package com.example.springbootrestcontrollerangular.dao;



import com.example.springbootrestcontrollerangular.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseDao  extends JpaRepository<Course,Long> {

    Page<Course> findCourseByCourseNameContains(String keyword, Pageable pageable);

    @Query(value = "select * from courses as c where c.course_id IN (SELECT e.course_id from enrolled_in as e where e.student_id =:studentId)",nativeQuery = true)
    Page<Course> getCourseByStudentId(@Param("studentId") Long studentId , Pageable pageable);

    @Query(value = "select * from courses as c where c.course_id not in (SELECT e.course_id from enrolled_in as e where e.student_id =:studentId)",nativeQuery = true)
    Page<Course> getNonEnrolledInCourseByStudentId(@Param("studentId") Long studentId , Pageable pageable);

    @Query(value = "select c from Course as c where c.instructor.instructorId=:instructorId")
    Page<Course> getCoursesByInstructorId(@Param("instructorId") Long instructorId, Pageable pageable);
}
