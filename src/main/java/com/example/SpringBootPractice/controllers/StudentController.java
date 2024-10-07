package com.example.SpringBootPractice.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.SpringBootPractice.beans.Student;

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

    @PutMapping("/students/{firstName}/{lastName}")
    public Student updateStudent(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName, @RequestBody Student student) {
        for (Student s : students) {
            if (s.getFirstName().equals(firstName) && s.getLastName().equals(lastName)) {
                s.setFirstName(student.getFirstName());
                s.setLastName(student.getLastName());
                return s;
            }
        }
        return null;
    }

    @DeleteMapping("/students/{firstName}/{lastName}")
    public Student deleteStudent(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
        for (Student s : students) {
            if (s.getFirstName().equals(firstName) && s.getLastName().equals(lastName)) {
                students.remove(s);
                return s;
            }
        }
        return null;
    } 

    
}
