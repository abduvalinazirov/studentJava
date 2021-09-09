package com.example.lessoncrud.controller;

import com.example.lessoncrud.Entity.User;
import com.example.lessoncrud.Repository.UserRepository;
import com.example.lessoncrud.payload.LoginRequest;
import com.example.lessoncrud.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserJwtController {
  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthenticationManager authenticationManager;

  public UserJwtController(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.jwtTokenProvider = jwtTokenProvider;
    this.authenticationManager = authenticationManager;
  }

  @PostMapping("/login")
  private ResponseEntity login(@RequestBody LoginRequest loginRequest) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    User user = userRepository.findByUsername(loginRequest.getUsername());
    if (user == null) {
      throw new UsernameNotFoundException("User note found");
    }
    String token = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
    Map<Object, Object> map = new HashMap<>();
    map.put("username", user.getUsername());
    map.put("token", token);
    return ResponseEntity.ok(map);
  }

}
