package com.icesi.demoBoot.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.icesi.demoBoot.beans.Student;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {
    
    public static List<Student> students = new ArrayList<Student>();

    public StudentController() {
        students.add(new Student("John", "Doe"));
        students.add(new Student("Jane", "Johnson"));
        students.add(new Student("Alice", "Smith"));
        students.add(new Student("Bob", "Brown"));
        students.add(new Student("Charlie", "Davis"));
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        return students;
    }

    @GetMapping("/students/{firstName}/{lastName}")
    public Student studentsPathVariable(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
        return new Student(firstName, lastName);
    }

    @GetMapping("/students/query")
    public Student studentsQuery(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        return new Student(firstName, lastName);
    }

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        students.add(student);
        return student;
    }
}
