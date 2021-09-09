package com.example.lessoncrud.Service;

import com.example.lessoncrud.Entity.User;
import com.example.lessoncrud.Repository.RoleRepository;
import com.example.lessoncrud.Repository.UserRepository;
import com.example.lessoncrud.payload.UserRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final RoleRepository roleRepository;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.roleRepository = roleRepository;
  }


  public boolean addUser(UserRequest userRequest) {
    User user = new User();
    user.setUsername(userRequest.getUsername());
    user.setFirstname(userRequest.getFirstname());
    user.setLastname(userRequest.getLastname());
    user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
    user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
    System.out.println(roleRepository.findByName("ROLE_USER"));
    return userRepository.save(user) != null;
  }

  public boolean deleteUser(Long id) {
    userRepository.deleteById(id);
    return true;
  }

  public List<User> getUsers() {
    return userRepository.findAll();
  }
}
