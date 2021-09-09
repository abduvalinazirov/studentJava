package com.example.lessoncrud.Service;

import com.example.lessoncrud.Entity.Student;
import com.example.lessoncrud.Repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
  private final StudentRepository studentRepository;

  public StudentService(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  public Student addStudent(Student student) {
    return studentRepository.save(student);
  }

  public List<Student> getAllStudents() {
    return studentRepository.findAll();
  }

  public Student showStudent(String name) {
    return studentRepository.findByNameQuery(name);
  }

  public List<Student> showStudentByUsername(String username) {
    return studentRepository.findByUsername(username);
  }

  public List<Student> getLikeStudent(String name) {
    System.out.println("salom");
    return studentRepository.findAllByNameLike(name);
  }

  public List<Student> getLikeStartSurname(String lastname) {
    return studentRepository.findAllByLastnameStartingWith(lastname);
  }

  public void deleteStudent(Long id) {
    studentRepository.deleteById(id);
  }

}
