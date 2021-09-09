package com.example.lessoncrud.controller;

import com.example.lessoncrud.Entity.Student;
import com.example.lessoncrud.Repository.StudentRepository;
import com.example.lessoncrud.Service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StundentController {
  private final StudentService studentService;
  private final StudentRepository studentRepository;

  public StundentController(StudentService studentService, StudentRepository studentRepository) {
    this.studentService = studentService;
    this.studentRepository = studentRepository;
  }

  @PostMapping("/student")
  private ResponseEntity addStudent(@RequestBody Student student) {
    Student student1 = studentService.addStudent(student);
    return ResponseEntity.ok(student1);
  }

  @GetMapping("/getStudents")
  private ResponseEntity getAllStudents() {
    List<Student> students = studentService.getAllStudents();
    return ResponseEntity.ok(students);
  }

  @GetMapping("/getStudent/{name}")
  private ResponseEntity getStudent(@PathVariable String name) {
    return ResponseEntity.ok(studentService.showStudent(name));
  }

  @GetMapping("/student/{username}")
  private ResponseEntity getStudentByUsername(@PathVariable String username) {
    return ResponseEntity.ok(studentService.showStudentByUsername(username));
  }

  @GetMapping("/student/search")
  private ResponseEntity getStudentStartSurname(@RequestParam String lastname) {
    return ResponseEntity.ok(studentService.getLikeStartSurname(lastname));
  }

  @PutMapping("/student")
  private ResponseEntity updateStudent(@RequestBody Student student) {
    return ResponseEntity.ok(studentRepository.save(student));
  }

  @DeleteMapping("/student/{id}")
  private ResponseEntity deleteStudent(@PathVariable Long id) {
    studentService.deleteStudent(id);
    return ResponseEntity.ok("O'chirildi");
  }
}
