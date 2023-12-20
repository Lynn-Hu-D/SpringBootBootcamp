package com.ltp.gradesubmission.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;


import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.exception.StudentNotFoundException;
import com.ltp.gradesubmission.repository.StudentRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor 
public class StudentServiceImpl implements StudentService {

    // @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student getStudent(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        // if (student.isPresent()) {
        //     return student.get();
        // } else {
        //     throw new StudentNotFoundException(id);
        // }
        return unwrapStudent(student, id);
    
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {  
        studentRepository.deleteById(id);      
    }

    @Override
    public List<Student> getStudents() {
        return (List<Student>) studentRepository.findAll();   
    }

    // void printGrades(Student student) {
    //     for (Grade grade : student.getGrades()) {
    //         System.out.println(grade.getScore());
    //     }
    // }

    static Student unwrapStudent(Optional<Student> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new StudentNotFoundException(id);
    }

}