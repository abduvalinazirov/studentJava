package com.example.lessoncrud.controller;

import com.example.lessoncrud.Service.UserService;
import com.example.lessoncrud.payload.UserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/add")
  private ResponseEntity save(@RequestBody UserRequest userRequest) {
    return userService.addUser(userRequest) ? ResponseEntity.ok("User saqlandi") : new ResponseEntity("Xatolik", HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @DeleteMapping("/delete/{id}")
  private ResponseEntity delete(@PathVariable Long id) {
    userService.deleteUser(id);
    return ResponseEntity.ok("O'chirildi");
  }

  @GetMapping("/getAll")
  private ResponseEntity getAllStudents() {
    return userService.getUsers() != null ? ResponseEntity.ok(userService.getUsers()) : new ResponseEntity("Xatolik", HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
