package com.ltp.gradesubmission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class GradeController {
    List<Grade> studentGrades = new ArrayList<>();
  

    @GetMapping("/grades")
    public String getGrades(Model model){
        model.addAttribute("grades", studentGrades);
        return "grades";
    }

    @GetMapping("/")
    public String getForms(Model model, @RequestParam(required=false) String id){
        // Grade grade;
        // if (getGradeindex(name) == -1000){
        //     grade = new Grade();
        // } else {
        //     grade = studentGrades.get(getGradeindex(name));
        // }
        Integer index = getGradeindex(id);
        model.addAttribute("grade", index == Constants.NOT_FOUND ? new Grade() : studentGrades.get(index));
        return "form";
    }

    @PostMapping("/handleSubmit")
    public String submitGrade(Grade grade){
        int index = getGradeindex(grade.getId());
        if (index == Constants.NOT_FOUND){
            studentGrades.add(grade);
        } else {
            studentGrades.set(index, grade);
        }
        
        return "redirect:/grades";
    }

    public Integer getGradeindex(String id){
        for (int i = 0; i < studentGrades.size(); i++){
            if (studentGrades.get(i).getId().equals(id)){
                return i;
            }
        }
        return Constants.NOT_FOUND;
    }
    
}
