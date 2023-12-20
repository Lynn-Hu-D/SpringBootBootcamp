package com.ltp.gradesubmission.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Grade;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.exception.CourseNotFoundException;
import com.ltp.gradesubmission.exception.GradeNotFoundException;
import com.ltp.gradesubmission.exception.StudentNotFoundException;
import com.ltp.gradesubmission.repository.CourseRepository;
import com.ltp.gradesubmission.repository.GradeRepository;
import com.ltp.gradesubmission.repository.StudentRepository;

import lombok.AllArgsConstructor;

import java.util.Optional;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor // AVOID autowired
public class GradeServiceImpl implements GradeService {
    // @Autowired
   GradeRepository gradeRepository;
   StudentRepository studentRepository;
   CourseRepository courseRepository;

    //  can avoid autowired many fields
    // public GradeServiceImpl(GradeRepository gradeRepository, StudentRepository studentRepository) {
    //     this.gradeRepository = gradeRepository;
    //     this.studentRepository = studentRepository;
    // }
    

    @Override
    public Grade getGrade(Long studentId, Long courseId) {
        Optional<Grade> grade = gradeRepository.findByStudentIdAndCourseId(studentId, courseId);
        // if (grade.isPresent()) {
        //     return grade.get();
        // } else {
        //     throw new GradeNotFoundException(studentId, courseId);
        // }
        return unwrapGrade(grade, studentId, courseId); 
    }

    @Transactional // ensure that all the changes are committed or none
    @Override
    public Grade saveGrade(Grade grade, Long studentId, Long courseId) {
        // Optional<Student> student = studentRepository.findById(studentId);
        // Optional<Course> course = courseRepository.findById(courseId);
        // if (student.isPresent() && course.isPresent()) {
        //     grade.setStudent(student.get());
        //     grade.setCourse(course.get());
        //     return gradeRepository.save(grade);   
        // } else if (!student.isPresent()) {
        //     throw new StudentNotFoundException(studentId);
        // } else {
        //     throw new CourseNotFoundException(courseId);
        // }
       
        Student student = StudentServiceImpl.unwrapStudent(studentRepository.findById(studentId), studentId);
        Course course = CourseServiceImpl.unwrapCourse(courseRepository.findById(courseId), courseId);
        grade.setStudent(student);
        grade.setCourse(course);
        return gradeRepository.save(grade);
    }

    @Override
    public Grade updateGrade(String score, Long studentId, Long courseId) {
        Optional<Grade> grade = gradeRepository.findByStudentIdAndCourseId(studentId, courseId);
        // if (grade.isPresent()) {
        //     Grade unwrappedGrade = grade.get();
        //     unwrappedGrade.setScore(score);
        //     return gradeRepository.save(unwrappedGrade);
        // } else {
        //     throw new GradeNotFoundException(studentId, courseId);
        // }
        Grade unwrappedGrade = unwrapGrade(grade, studentId, courseId);
        unwrappedGrade.setScore(score);
        return gradeRepository.save(unwrappedGrade);

    }

    @Override
    public void deleteGrade(Long studentId, Long courseId) {
        gradeRepository.deleteByStudentIdAndCourseId(studentId, courseId);
    }

    @Override
    public List<Grade> getStudentGrades(Long studentId) {
        return gradeRepository.findByStudentId(studentId);
           
    }

    @Override
    public List<Grade> getCourseGrades(Long courseId) {
        return gradeRepository.findByCourseId(courseId);
    }

    @Override
    public List<Grade> getAllGrades() {
        return (List<Grade>) gradeRepository.findAll();
    }


    static Grade unwrapGrade(Optional<Grade> entity, Long studentId, Long courseId) {
        if (entity.isPresent()) return entity.get();
        else throw new GradeNotFoundException(studentId, courseId);
    }
}
