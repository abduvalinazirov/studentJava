package com.example.lessoncrud.payload;

import com.example.lessoncrud.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class UserRequest {
  private String firstname;
  private String lastname;
  private String username;
  private String password;
  private List<Role> roles;
}