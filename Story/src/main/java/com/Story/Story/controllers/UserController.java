package com.Story.Story.controllers;

import com.Story.Story.models.User;
import com.Story.Story.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  @Autowired IUserRepository userRepository;

  @GetMapping
  public List<User> getAllUsers() {
    List<User> users = new ArrayList<>();
    try {
      users = userRepository.findAll();
    }
    catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
    return users;
  }

  @GetMapping("/{id}")
  public User getUserById(@PathVariable Long id) {
    try {
      var user = userRepository.findById(id);
      return user.orElse(null);
    }
    catch (Exception ex) {
      System.out.println("UnKnow error occured " + ex.getMessage());
      return null;
    }
  }

  @PostMapping
  public User addNewUser(@RequestBody User newUser) {
    try {
      return userRepository.save(newUser);
    }
    catch (Exception ex) {
      System.out.println(ex.getMessage());
      return null;
    }
  }

  @PutMapping
  @RequestMapping("/{id}")
  public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
    try {
      var user = getUserById(id);
      if (user == null) {
        System.out.println("User Does not Exists with id " + id);
        return null;
      }

      if (updatedUser.getFirstName() != null)
        user.setFirstName(updatedUser.getFirstName());
      if (updatedUser.getLastName() != null)
        user.setLastName(updatedUser.getLastName());
      if (updatedUser.getUsername() != null)
        user.setUsername(updatedUser.getUsername());
      if (updatedUser.getPassword() != null)
        user.setPassword(updatedUser.getPassword());
      return userRepository.save(user);
    }
    catch (Exception ex) {
      System.out.println("UnKnow error occured " + ex.getMessage());
      return null;
    }
  }
}
