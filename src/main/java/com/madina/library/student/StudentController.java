package com.madina.library.student;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/search")
    public List<Student> searchStudents(@RequestParam String lastName) {
        return studentRepository.findByLastNameContainingIgnoreCase(lastName);
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
        Student savedStudent = studentRepository.save(student);
        return ResponseEntity
                .created(URI.create("/api/students/" + savedStudent.getId()))
                .body(savedStudent);
    }
}
