package com.dev.userserver.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.userserver.model.User;
import com.dev.userserver.repository.UserRepo;

@Service
public class UserService {
  
  @Autowired
  private UserRepo userRepository;

  public Optional<User> findUserByEmail(String email){
    return userRepository.findByEmail(email);
  }

  public List<User> findAllUser(){
    return userRepository.findAll();
  }

  public User saveUser(User user){
    return userRepository.save(user);
  }

  public void deleteUser(User user){
    userRepository.deleteById(user.getId());
  }

  public boolean isEmailAlreadyExists(User newUser, List<User> users) {
    return users.stream()
            .anyMatch(existingUser -> existingUser.getEmail().equals(newUser.getEmail()));
}
}
