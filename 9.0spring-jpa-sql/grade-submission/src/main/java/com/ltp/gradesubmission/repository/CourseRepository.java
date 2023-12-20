package com.ltp.gradesubmission.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ltp.gradesubmission.entity.Course;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long>{

    
}