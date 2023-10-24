package com.dev.userserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.userserver.exception.Code;
import com.dev.userserver.exception.UserServerException;
import com.dev.userserver.model.User;
import com.dev.userserver.repository.UserRepo;
import com.dev.userserver.service.UserService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@RestController
@RequestMapping
public class UserController {

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private UserService userService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @PostMapping("/user/save")
  public ResponseEntity<Object> saveUser(@RequestBody User user){
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    List<User> users = userService.findAllUser();
    if(userService.isEmailAlreadyExists(user, users))
      throw new UserServerException(Code.EMAIL_ALREADY_EXISTS);
    User result = userService.saveUser(user);
    if(result.getId() > 0){
      return ResponseEntity.ok().body("User is saved!");
    }
    else{
      throw new UserServerException(Code.USER_NOT_SAVED);
    }
  }
  @GetMapping("/users/all")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<Object> getAllUsers(){
    return ResponseEntity.ok(userService.findAllUser());
  }

  @GetMapping("/users/userInfo")
  @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
  public ResponseEntity<Object> getUserDetails(){
    return ResponseEntity.ok(userService.findUserByEmail(getLoggedInUserDetails().getUsername()));
  }

  @PatchMapping("/users/userInfo/edit/email")
  @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
  public ResponseEntity<Object> changeUserEmail(@RequestParam String email){
    User target = userService.findUserByEmail(getLoggedInUserDetails().getUsername()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    target.setEmail(email);
    entityManager.merge(target);
    return ResponseEntity.ok(target);
  }

  @PatchMapping("/users/userInfo/edit/password")
  @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
  public ResponseEntity<Object> changeUserPassword(@RequestParam String password){
    User target = userService.findUserByEmail(getLoggedInUserDetails().getUsername()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    target.setPassword(password);
    entityManager.merge(target);
    return ResponseEntity.ok(target);
  }

  @DeleteMapping("/users/userInfo/edit/delete")
  @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
  public ResponseEntity<Object> deleteUser(@RequestParam String password){
    User target = userService.findUserByEmail(getLoggedInUserDetails().getUsername()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    userService.deleteUser(target);
    return ResponseEntity.accepted().body("User is deleted successfully!");
  }

  private UserDetails getLoggedInUserDetails(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if(authentication != null && authentication.getPrincipal() instanceof UserDetails){
      return (UserDetails) authentication.getPrincipal();
    }
    return null;
  }
}
